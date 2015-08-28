package com.coshine.ccs.bean.cpm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.bouncycastle.bcpg.ArmoredOutputStream;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openpgp.PGPCompressedData;
import org.bouncycastle.openpgp.PGPCompressedDataGenerator;
import org.bouncycastle.openpgp.PGPEncryptedData;
import org.bouncycastle.openpgp.PGPEncryptedDataGenerator;
import org.bouncycastle.openpgp.PGPException;
import org.bouncycastle.openpgp.PGPLiteralData;
import org.bouncycastle.openpgp.PGPPublicKey;
import org.bouncycastle.openpgp.PGPUtil;
import org.bouncycastle.openpgp.operator.bc.BcPGPDataEncryptorBuilder;
import org.bouncycastle.openpgp.operator.bc.BcPublicKeyKeyEncryptionMethodGenerator;

import com.coshine.acl.AccessService;
import com.coshine.ccs.bean.BaseSessionBean;
import com.coshine.ccs.bean.HSMManagerLong;
import com.coshine.ccs.bean.TransactionMgr;
import com.coshine.ccs.core.MsgMgr;
import com.coshine.ccs.core.dbobj.DBE_Exception;
import com.coshine.ccs.core.dbobj.JoinDBObject;
import com.coshine.ccs.core.dbobj.StatisticsDBObject;
import com.coshine.ccs.dbobj.DB_CP_BTHCIF;
import com.coshine.ccs.dbobj.DB_CP_COPMST;
import com.coshine.ccs.dbobj.DB_CP_CRDLNK;
import com.coshine.ccs.dbobj.DB_CP_CRDTBL;
import com.coshine.ccs.dbobj.DB_CP_CSTTBL;
import com.coshine.ccs.dbobj.DB_CP_NEWCRD;
import com.coshine.ccs.dbobj.DB_CP_PLSTIC;
import com.coshine.ccs.dbobj.DB_CP_REPLCN;
import com.coshine.ccs.dbobj.DB_CP_SYSPRM;
import com.coshine.ccs.function.ProcessDate;
import com.coshine.ccs.obj.OB_CP_COPMST;
import com.coshine.ccs.obj.OB_CP_CRDLNK;
import com.coshine.ccs.obj.OB_CP_CRDTBL;
import com.coshine.ccs.obj.OB_CP_NEWCRD;
import com.coshine.ccs.obj.OB_CP_PLSTIC;
import com.coshine.ccs.obj.OB_CP_REPLCN;
import com.coshine.ccs.to.ListPageForm;
import com.coshine.util.PGPKeyUtil;
import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.db.DBException;
import com.jcorporate.expresso.core.dbobj.MultiDBObject;
import com.jcorporate.expresso.core.misc.ConfigManager;

public class CardCollection extends BaseSessionBean implements
		java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private static org.apache.log4j.Logger log = org.apache.log4j.Logger
			.getLogger(CardCollection.class);

//	private static final String separator = System
//			.getProperty("file.separator");

	public final static String GET_EMBOSSING_FILE = "EmbFil";

	public final static String NEW_CARD_ACTIVATION = "CrdAct";

	public final static String SET_TO_COLLECTED = "SetCol";

	public CardCollection(){}

	public boolean canGetEmbossingFile() {
		boolean b = false;
		try {
			b = AccessService.checkAccess(user, GET_EMBOSSING_FILE);
		} catch (DBException e) {
			e.printStackTrace();
		} catch (DBE_Exception e) {
			e.printStackTrace();
		}
		return b;
	}
	
	protected String errorKey=null;
	
	protected void setError(String key){
		this.errorKey=key;
	}
	
	public boolean canNewCardActivation() {
		boolean b = false;
		try {
			b = AccessService.checkAccess(user, NEW_CARD_ACTIVATION);
		} catch (DBException e) {
			e.printStackTrace();
		} catch (DBE_Exception e) {
			e.printStackTrace();
		}
		return b;
	}

	public boolean canSetToCollected() {
		boolean b = false;
		try {
			b = AccessService.checkAccess(user, SET_TO_COLLECTED);
		} catch (DBException e) {
			e.printStackTrace();
		} catch (DBE_Exception e) {
			e.printStackTrace();
		}
		return b;
	}

	/**
	 * pinmailer
	 */
	public long countPrintByBatch(String batchNo) throws DBE_Exception {
		StatisticsDBObject sdb = new StatisticsDBObject();
		List result = sdb.getMultiStatistics("select count(*) from CP_NEWCRD where NC_BATCH_NO='"+ batchNo.trim() + "'");
		String row[] = (String[]) result.get(0);
		return Long.parseLong(row[0]);
	}

	//@SuppressWarnings("unchecked")
	public ListPageForm listRecord(Map param, int pageNum)throws Exception {
		boolean setDate0 = false;
		boolean setDate1 = false;
		JoinDBObject joinObj = new JoinDBObject();
		DB_CP_CRDTBL crd = new DB_CP_CRDTBL();
		DB_CP_NEWCRD db = new DB_CP_NEWCRD(user);
		joinObj.addDBObj(crd, "CP_CRDTBL");
		joinObj.addDBObj(db, "CP_NEWCRD");
		joinObj.setForeignKey("CP_CRDTBL", "CB_CARDHOLDER_NO", "CP_NEWCRD","NC_CARD_NO");
		// db.setMaxRecords(pageLimit);
		String batchNo = (String) param.get("varBatchNo");
		String Cardno = (String) param.get("NC_CARD_NO");
		String branchID = (String) param.get("NC_BRANCH_ID");
		String dateOpen = (String) param.get("NC_TIME0");
		String dateClose = (String) param.get("NC_TIME1");
		String emergency = (String) param.get("NC_EMERGENCY");
		String status = (String) param.get("NC_STATUS");
		String plasticCode = (String) param.get("active");
		String pickDate = (String) param.get("NC_PICK_DATE");
		String noselectid = (String) param.get("NOSELECT_ID");
		String selectid=(String)param.get("SELECT_ID");
		String crdFlag=(String)param.get("crdFlag");
//        ////System.out.println("varBatchNo:"+batchNo);
//        ////System.out.println("NC_CARD_NO:"+Cardno);
//        ////System.out.println("branchID:"+branchID);
//        ////System.out.println("dateOpen:"+dateOpen);
//        ////System.out.println("dateClose:"+dateClose);
//        ////System.out.println("emergency:"+emergency);
//        ////System.out.println("status:"+status);
//        ////System.out.println("plasticCode:"+plasticCode);
//        ////System.out.println("pickDate:"+pickDate);
//        ////System.out.println("noselectid:"+noselectid);
//        ////System.out.println("selectid:"+selectid);
		
//		Set noset=new HashSet();
//		String[] noselectidArr=noselectid.split(",");
//		List nolist = Arrays.asList(noselectidArr);
//		noset.addAll(nolist);
//		
//		Set selectset=new HashSet();
//		String[] selectidArr=selectid.split(",");
//		List selectlist = Arrays.asList(selectidArr);
//		selectset.addAll(selectlist);
		
		if (crdFlag != null && crdFlag.trim().length() > 0) {
			if("1".equals(crdFlag)){
				joinObj.setField("CP_CRDTBL", "CB_ID_TYPE", "<>  'P'");
			}else{
				joinObj.setField("CP_CRDTBL", "CB_ID_TYPE", crdFlag);
			}			
		}       
		if (batchNo != null && batchNo.trim().length() > 0) {
			joinObj.setField("CP_NEWCRD", "NC_BATCH_NO", batchNo + "%");
		}
		// else {
		if (Cardno != null && Cardno.trim().length() > 0) {
			joinObj.setField("CP_NEWCRD", "NC_CARD_NO", Cardno);
		}
		if (branchID != null && branchID.trim().length() > 0&&!branchID.equalsIgnoreCase("99999")) {
			joinObj.setField("CP_NEWCRD", "NC_BRANCH_ID", branchID);
		}
		if (emergency != null && emergency.trim().length() > 0) {
			joinObj.setField("CP_NEWCRD", "NC_EMERGENCY", emergency);
		}
		if (status != null && status.trim().length() > 0) {
			joinObj.setField("CP_NEWCRD", "NC_STATUS", status);
		}
		if (pickDate != null && pickDate.trim().length() > 0) {
			joinObj.setField("CP_NEWCRD", "NC_PICK_DATE", " ");
		}
		if (plasticCode != null) {
			joinObj.setField("CP_CRDTBL", "CB_NEW_PLASTIC_CD", plasticCode);
		}
		if (dateOpen != null && dateOpen.trim().length() > 0) {
			setDate0 = true;
		}
		if (dateClose != null && dateClose.trim().length() > 0) {
			setDate1 = true;
		}
		if (setDate0 && setDate1) {
			joinObj.setField("CP_NEWCRD", "NC_TIME", "between '" + dateOpen
					+ "0000' and '" + dateClose + "9999'");
		} else if (setDate0) {
			joinObj.setField("CP_NEWCRD", "NC_TIME", ">'" + dateOpen + "0000'");
		} else if (setDate1) {
			joinObj.setField("CP_NEWCRD", "NC_TIME", "<'" + dateClose+ "9999'");
		}
		// } 
		System.out.println(joinObj.getCustomFromClause());
		System.out.println(joinObj.getCustomWhereClause());
        System.out.println(joinObj.buildWhereClause(true));
		//begin
		long allCount=joinObj.doCount();
		if(allCount>4000){
			throw new Exception("对不起,查询结果太大,有["+allCount+"]条数据,请缩小查询的时间范围和分行范围!");
		}
		//end			
		List alllist = joinObj.searchAndRetrieveList("NC_TIME|NC_CARD_NO");
		StringBuffer buf = new StringBuffer();
		MultiDBObject temp = null;
		String tempStr = null;
		for (int i = 0; i < alllist.size(); i++) {
			temp = (MultiDBObject) alllist.get(i);
			tempStr = temp.getField("CP_NEWCRD", "NC_CARD_NO");
			buf.append(tempStr).append(",");
		}
		if (buf.indexOf(",") != -1) {
			buf.deleteCharAt(buf.lastIndexOf(","));
		}
		List list = joinObj.doList(pageNum, "NC_TIME|NC_CARD_NO");
		MultiDBObject oneMulti = null;
		List ret = new ArrayList(list.size());
		for (int i = 0; i < list.size(); i++) {
			oneMulti = (MultiDBObject) list.get(i);
			Map fields = new HashMap();
			fields.put("NC_CARD_NO", oneMulti.getField("CP_NEWCRD","NC_CARD_NO"));
			fields.put("NC_STATUS", oneMulti.getField("CP_NEWCRD","NC_STATUS"));
			fields.put("NC_TIME", oneMulti.getField("CP_NEWCRD", "NC_TIME"));
			fields.put("NC_EMERGENCY", oneMulti.getField("CP_NEWCRD","NC_EMERGENCY"));
			fields.put("CB_NEW_PLASTIC_CD", oneMulti.getField("CP_CRDTBL","CB_NEW_PLASTIC_CD"));
			fields.put("NC_BATCH_NO", oneMulti.getField("CP_NEWCRD","NC_BATCH_NO"));
			fields.put("CB_CARD_PRDCT_GROUP", oneMulti.getField("CP_CRDTBL","CB_CARD_PRDCT_GROUP"));
			if (i == 0) {
//				String[] tempnoArr=(String[]) noset.toArray(new String[0]);
//				StringBuffer bufno=new StringBuffer();
//				for(int j=0;j<tempnoArr.length;j++){
//					bufno.append(tempnoArr[j]).append(",");
//				}
//				if(bufno.length()>0){
//					bufno.deleteCharAt(bufno.lastIndexOf(","));
//				}
//				
//				String[] tempArr=(String[]) selectset.toArray(new String[0]);
//				StringBuffer selectbuf=new StringBuffer();
//				for(int j=0;j<tempArr.length;j++){
//					selectbuf.append(tempArr[j]).append(",");
//				}
//				if(selectbuf.length()>0){
//					selectbuf.deleteCharAt(selectbuf.lastIndexOf(","));
//				}				
				fields.put("ALL_SELID", buf.toString());
				fields.put("NOSELECT_ID",noselectid);
				fields.put("SELECT_ID", selectid);
			}
			ret.add(fields);
		}
		ListPageForm page = new ListPageForm(ret, pageNum, joinObj.getPageLimit(), joinObj.doCount());
		return page;
	}
	
	public ListPageForm listRecordPrintMail(Map param, int pageNum)throws Exception {
		boolean setDate0 = false;
		boolean setDate1 = false;
		JoinDBObject joinObj = new JoinDBObject();
		DB_CP_CRDTBL crd = new DB_CP_CRDTBL(user);
		DB_CP_NEWCRD db = new DB_CP_NEWCRD(user);
		DB_CP_BTHCIF df = new DB_CP_BTHCIF(user);
		joinObj.addDBObj(crd, "CP_CRDTBL");
		joinObj.addDBObj(db, "CP_NEWCRD");
		joinObj.addDBObj(df, "CP_BTHCIF");
		joinObj.setForeignKey("CP_CRDTBL", "CB_CARDHOLDER_NO", "CP_NEWCRD","NC_CARD_NO");
		joinObj.setForeignKey("CP_CRDTBL", "CB_CARDHOLDER_NO", "CP_BTHCIF","CB_CARDNO");
		String batchNo = (String) param.get("varBatchNo");
		String Cardno = (String) param.get("NC_CARD_NO");
		String branchID = (String) param.get("NC_BRANCH_ID");
		String dateOpen = (String) param.get("NC_TIME0");
		String dateClose = (String) param.get("NC_TIME1");
		String status = (String) param.get("NC_STATUS");
		String plasticCode = (String) param.get("active");
		String noselectid = (String) param.get("NOSELECT_ID");
		String selectid=(String)param.get("SELECT_ID");
		String crdFlag=(String)param.get("crdFlag");
		
		//joinObj.setField("CP_CRDTBL", "CB_ID_TYPE", crdFlag);
		joinObj.setField("CP_BTHCIF", "CB_UPLOADRESULT", "Y");//上送成功的记录
		joinObj.setField("CP_CRDTBL", "CB_PLASTIC_CD", "U");//排除未激活
		if (batchNo != null && batchNo.trim().length() > 0) {
			joinObj.setField("CP_NEWCRD", "MODIFY_BY", batchNo);
		}
		if (Cardno != null && Cardno.trim().length() > 0) {
			joinObj.setField("CP_NEWCRD", "NC_CARD_NO", Cardno);
		}
		if (branchID != null && branchID.trim().length() > 0&&!branchID.equalsIgnoreCase("99999")) {
			joinObj.setField("CP_NEWCRD", "NC_BRANCH_ID", branchID);
		}
		if (setDate0 && setDate1) {
			joinObj.setField("CP_BTHCIF", "CB_TIMESTAMP", "between '" + dateOpen
					+ "0000' and '" + dateClose + "9999'");
		} else if (setDate0) {
			joinObj.setField("CP_BTHCIF", "CB_TIMESTAMP", ">'" + dateOpen + "0000'");
		} else if (setDate1) {
			joinObj.setField("CP_BTHCIF", "CB_TIMESTAMP", "<'" + dateClose+ "9999'");
		}
		joinObj.setCustomWhereClause(joinObj.buildWhereClause(true)+" and trim(cp_bthcif.cb_batch_file)=trim(cp_newcrd.modify_by)");
		System.out.println(joinObj.getCustomWhereClause());
        System.out.println(joinObj.buildWhereClause(true));
		//begin
		long allCount=joinObj.doCount();
		if(allCount>4000){
			throw new Exception("对不起,查询结果太大,有["+allCount+"]条数据,请缩小查询的时间范围和分行范围!");
		}
		//end			
		List alllist = joinObj.searchAndRetrieveList("NC_TIME|NC_CARD_NO");
		StringBuffer buf = new StringBuffer();
		MultiDBObject temp = null;
		String tempStr = null;
		for (int i = 0; i < alllist.size(); i++) {
			temp = (MultiDBObject) alllist.get(i);
			tempStr = temp.getField("CP_NEWCRD", "NC_CARD_NO");
			buf.append(tempStr).append(",");
		}
		if (buf.indexOf(",") != -1) {
			buf.deleteCharAt(buf.lastIndexOf(","));
		}
		List list = joinObj.doList(pageNum, "NC_TIME|NC_CARD_NO");
		MultiDBObject oneMulti = null;
		List ret = new ArrayList(list.size());
		for (int i = 0; i < list.size(); i++) {
			oneMulti = (MultiDBObject) list.get(i);
			Map fields = new HashMap();
			fields.put("NC_CARD_NO", oneMulti.getField("CP_NEWCRD","NC_CARD_NO"));
			fields.put("NC_STATUS", oneMulti.getField("CP_NEWCRD","NC_STATUS"));
			fields.put("NC_TIME", oneMulti.getField("CP_BTHCIF", "CB_TIMESTAMP"));
			fields.put("NC_EMERGENCY", oneMulti.getField("CP_NEWCRD","NC_CHECK_FLAG"));
			fields.put("CB_NEW_PLASTIC_CD", oneMulti.getField("CP_CRDTBL","CB_NEW_PLASTIC_CD"));
			fields.put("NC_BATCH_NO", oneMulti.getField("CP_NEWCRD","NC_BATCH_NO"));
			fields.put("CB_CARD_PRDCT_GROUP", oneMulti.getField("CP_CRDTBL","CB_CARD_PRDCT_GROUP"));
			if (i == 0) {
				fields.put("ALL_SELID", buf.toString());
				fields.put("NOSELECT_ID",noselectid);
				fields.put("SELECT_ID", selectid);
			}
			ret.add(fields);
		}
		ListPageForm page = new ListPageForm(ret, pageNum, joinObj.getPageLimit(), joinObj.doCount());
		return page;
	}


	public int setCollectStatus(String[] cardnos, String status[])throws Exception {
		try {
			TransactionMgr.currentSession();
			TransactionMgr.beginTransaction();
			DB_CP_NEWCRD db = new DB_CP_NEWCRD(TransactionMgr.currentSession(),user);
			OB_CP_NEWCRD ob = null;
			for (int i = 0; i < cardnos.length; i++) {
				ob = new OB_CP_NEWCRD();
				ob.NC_CARD_NO(cardnos[i]);
				ob.NC_STATUS(status[i]);
				db.doUpdate(ob);
			}
			TransactionMgr.commit();
			return 0;
		} catch (Exception e) {
			log.error("", e);
			TransactionMgr.rollback();
			throw e;
		} finally {
			TransactionMgr.closeSession();
		}
	}

	/**
	 * @return error message list['card number','error message']
	 */
	//@SuppressWarnings("unchecked")
	public List activeCards(String cardnos[]) throws Exception {
		List ret = new ArrayList();
		try {
			TransactionMgr.currentSession();
			TransactionMgr.beginTransaction();

			for (int i = 0; i < cardnos.length; i++) {
				String errKey = _activeCard(cardnos[i]);
				if (errKey != null) {
					ret.add(new String[] { cardnos[i],
							MsgMgr.getError(language, errKey) });
				}
			}
			TransactionMgr.commit();
			return ret;
		} catch (Exception e) {
			log.error("", e);
			TransactionMgr.rollback();
			throw e;
		} finally {
			TransactionMgr.closeSession();
		}
	}

	/**
	 *
	 * 
	 * @param cardnos
	 * @return
	 */
	//@SuppressWarnings("unchecked")
	public String getEmbossingFile(String cardnos[], String realPath,String filename)
			throws Exception {
		////log.debug("getEmbossingFile :: start :: user :: " + user);
		//String subPath = "tmp/FRST" + user + "_emfile.txt";
		HSMManagerLong hsm=null;
		String subPath =filename+".zip";
		
		File tempDir = new File(realPath);
		if (!tempDir.exists()) {
			log.info("Filepath:" +realPath+ " doesn't exist, create it first!");
			tempDir.mkdirs();
		}

		try {
			
			TransactionMgr.currentSession();
			TransactionMgr.beginTransaction();

			hsm = new HSMManagerLong();
			hsm.createHSMConnection();

			DB_CP_CRDTBL dbTbl = new DB_CP_CRDTBL();
			OB_CP_CRDTBL obTbl ;
			
			DB_CP_NEWCRD dbNewCrd = new DB_CP_NEWCRD();
			DB_CP_CSTTBL dbCstTbl = new DB_CP_CSTTBL();
			DB_CP_SYSPRM dbSysPrm = new DB_CP_SYSPRM();
			
			String nextProCessingDate="";
			String yddd = "";
			if(dbSysPrm.find()){
				Date date_yddd = new Date();
				SimpleDateFormat date_y = new SimpleDateFormat("yy");
				//yddd = (Integer.parseInt(date_y.format(date_yddd).substring(1,2).concat("000")) + dbSysPrm.getField("SP_PROCESSING_DDD"));
				yddd = (Integer.parseInt(date_y.format(date_yddd).substring(1,2)) + dbSysPrm.getField("SP_PROCESSING_DDD"));
				nextProCessingDate = dbSysPrm.getField("SP_NEXT_PROCESSING_DATE");
			}
			
			int length = yddd.length();
			int i1 = 4 -length;
			if(i1 > 0){
				StringBuffer sb = new StringBuffer();
				for(int j=0;j<i1;j++){
					sb.append("0");
				}
				yddd += sb.toString();
			}

//			DB_CP_UPLPHT aDB_CP_UPLPHT = new DB_CP_UPLPHT();
//			OB_CP_UPLPHT aOB_CP_UPLPHT = null;

			Map fileNameMap = new HashMap();
			
			ArrayList temp = new ArrayList();
			
			for (int i = 0; i < cardnos.length; i++) {
				obTbl = new OB_CP_CRDTBL();
				obTbl.CB_CARDHOLDER_NO(cardnos[i]);
				dbTbl.doFind(obTbl);
				
				dbNewCrd.setField("NC_CARD_NO", cardnos[i]);
				dbNewCrd.doFind();
				
				String newReason = dbNewCrd.getField("NC_CARD_REASON");
				
				/*����ݿ���ȡcvv key index*/
//				DB_CP_VERKEY dbKey=new DB_CP_VERKEY();
//				OB_CP_VERKEY obKey=new OB_CP_VERKEY();
//				obKey.VK_VALUE("CVKA");
//				if(!dbKey.doFindByKey(obKey)){
//					throw new Exception("no CVKA key index in database");
//				}
//				String idx=obKey.VK_KEY().trim();
//				if(idx.length()>2){
//					while(idx.startsWith("0")&&idx.length()>2){
//						idx=idx.substring(1,idx.length());
//					}
//				}
//				if(idx.length()<2){
//					while(idx.length()<2)idx="0"+idx;
//				}
//				
//				obTbl.CB_CVKI(new BigDecimal(idx));
//				dbTbl.doUpdate(obTbl);
				

				// [IND] - index
				
				
				//card product 
				
				String product_group = dbTbl.getField("CB_CARD_PRDCT_GROUP");
				
				dbCstTbl.setField("CB_ID_TYPE", dbTbl.getField("CB_ID_TYPE"));
				dbCstTbl.setField("CB_CUSTOMER_IDNO", dbTbl.getField("CB_IDNO"));
				dbCstTbl.doFind();
				
				String photo_ind = dbCstTbl.getField("CB_PHOTO_IND");
				String photo_name = "";
				
				if(!fileNameMap.containsKey(product_group)){
					String mkfiledate = "";
					Date mkFileDate = new Date();
					SimpleDateFormat nowDateFormat = new SimpleDateFormat("yyyyMMdd");
					mkfiledate=nowDateFormat.format(mkFileDate);
					if("Y".equalsIgnoreCase(photo_ind)){
						fileNameMap.put(product_group, filename+"_EMBP"+product_group+mkfiledate+".txt");
						photo_name = dbTbl.getField("CB_IDNO");
					}else{
						fileNameMap.put(product_group, filename+"_EMBN"+product_group+mkfiledate+".txt");
					}
					
					temp.add(fileNameMap.get(product_group));
				}
				
				if("Y".equalsIgnoreCase(photo_ind)){
					photo_ind = "1";
				}else{
					photo_ind = "0";
				}
				
				File file = new File(tempDir, fileNameMap.get(product_group).toString());
				
				SimpleDateFormat logTimeFormat=new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
				
				////log.debug("getEmbossingFile :: path :: " + file.getPath());
				////log.debug("Enter get embossing file Time:"+logTimeFormat.format(new Date()));
				
				FileOutputStream fos = new FileOutputStream(file, true);
				
				String no = String.valueOf(i + 1);
				int len = no.length();
				String index = "";
				for (int j = 0; j < 3 - len; j++)
					index += "0";
				index += no;

				// [From] - now time , ����/����
				String nowDate = "";
				Date date = new Date();
				SimpleDateFormat dateFormat = new SimpleDateFormat("MM");
				nowDate += dateFormat.format(date);
				nowDate += "/";
				dateFormat = new SimpleDateFormat("yy");
				nowDate += dateFormat.format(date);

				// expiry time
				String expiry_yymm;
				if(dbTbl.getField("CB_PLASTIC_CD").equals("U")){
					expiry_yymm = dbTbl.getField("CB_NEW_EXPIRY_CCYYMM");
				}else{
					expiry_yymm = dbTbl.getField("CB_EXPIRY_CCYYMM");
				}
				
				if (expiry_yymm.length() > 4)
					expiry_yymm = expiry_yymm.substring(expiry_yymm.length() - 4);
				else{
					expiry_yymm = "4912";//4912
					System.out.println("Expiry YYMM Error");
				}
				// emboss name
				String emboss_name = MsgMgr.convertShowString(dbTbl.getField("CB_EMBOSSNAME"));
				len = emboss_name.length();
				for (int j = 0; j < 24 - len; j++)
					emboss_name += " ";

				String cvv = null;
				////log.debug("start call hsm at:"+logTimeFormat.format(new Date()));
				String serviceCode="120";
				cvv=hsm.cvvGeneration1(cardnos[i], expiry_yymm, serviceCode);
				log.debug("end call hsm at:"+logTimeFormat.format(new Date()));
				log.debug("CVV generate:" + cvv);
				if (cvv == null) {
					throw new Exception("card " + cardnos[i]+ " can not generate cvv!");
				}
				String cvv2 = null;
				cvv2=hsm.cvv2Generation1(cardnos[i], expiry_yymm);
				log.debug("CVV2 generate:" + cvv2);
				if (cvv2 == null) {
					throw new Exception("card " + cardnos[i]+ " can not generate cvv2!");
				}
				
				String strAdd1 = "";
				String strAdd2 = "";
				String strAdd3 = "";
				String strAdd4 = "";
				String strAdd5 = "";
				
				if (dbTbl.getField("CB_BILL_ADDR_CD") == null
						|| dbTbl.getField("CB_BILL_ADDR_CD").trim().equals("1")){
					strAdd1 = dbCstTbl.getField("CB_ALT1_BILL_ADDR1");
					strAdd2 = dbCstTbl.getField("CB_ALT1_BILL_ADDR2");
					strAdd3 = dbCstTbl.getField("CB_ALT1_BILL_ADDR3");
					strAdd4 = dbCstTbl.getField("CB_ALT1_BILL_ADDR4");
					strAdd5 = dbCstTbl.getField("CB_ALT1_BILL_ADDR5");
				}else if(dbTbl.getField("CB_BILL_ADDR_CD").equalsIgnoreCase("H")){
					strAdd1 = dbCstTbl.getField("CB_HOME_ADDR1");
					strAdd2 = dbCstTbl.getField("CB_HOME_ADDR2");
					strAdd3 = dbCstTbl.getField("CB_HOME_ADDR3");
					strAdd4 = dbCstTbl.getField("CB_HOME_ADDR4");
					strAdd5 = dbCstTbl.getField("CB_HOME_ADDR5");
				}else if(dbTbl.getField("CB_BILL_ADDR_CD").equalsIgnoreCase("C")){
					if (dbTbl.getField("CB_CORPORATE_ID") != null&& !dbTbl.getField("CB_CORPORATE_ID").trim().equals("")){						
						OB_CP_COPMST aOB_CP_COPMST = new OB_CP_COPMST();
						DB_CP_COPMST aDB_CP_COPMST = new DB_CP_COPMST(user);
						aOB_CP_COPMST.CO_CORPORATE_ID(dbTbl.getField("CB_CORPORATE_ID"));
						
						if (!aDB_CP_COPMST.doFindByKey(aOB_CP_COPMST))
							this.setError("CCPM1900.NO_COPRATE_CARD");
						strAdd1 = aOB_CP_COPMST.CO_MAIL_ADD_LINE1();
						strAdd2 = aOB_CP_COPMST.CO_MAIL_ADD_LINE2();
						strAdd3 = aOB_CP_COPMST.CO_MAIL_ADD_LINE3();
						strAdd4 = aOB_CP_COPMST.CO_MAIL_ADD_LINE4();
						strAdd5 = aOB_CP_COPMST.CO_MAIL_ADD_LINE5();
					}else{
						strAdd1 = dbCstTbl.getField("CB_CO_ADDR1");
						strAdd2 = dbCstTbl.getField("CB_CO_ADDR2");
						strAdd3 = dbCstTbl.getField("CB_CO_ADDR3");
						strAdd4 = dbCstTbl.getField("CB_CO_ADDR4");
						strAdd5 = dbCstTbl.getField("CB_CO_ADDR5");
					}
				}else if(dbTbl.getField("CB_BILL_ADDR_CD").equalsIgnoreCase("2")){
					strAdd1 = dbCstTbl.getField("CB_ALT2_BILL_ADDR1");
					strAdd2 = dbCstTbl.getField("CB_ALT2_BILL_ADDR2");
					strAdd3 = dbCstTbl.getField("CB_ALT2_BILL_ADDR3");
					strAdd4 = dbCstTbl.getField("CB_ALT2_BILL_ADDR4");
					strAdd5 = dbCstTbl.getField("CB_ALT2_BILL_ADDR5");
				}

				/********************************************/
				
				String track2 = ";";
				track2 += cardnos[i];
				track2 += "=";
				track2 += expiry_yymm;
				track2 += "120";
//				track2 += "201";
				track2 += cvv;
				track2 +=ProcessDate.SP_NEXT_PROCESSING_DATE();
				track2 += "?";				
				//track3				
				String track3 = ";";
				track3 += "99";
				track3 += cardnos[i];
				track3 += "=156156099999999";
				track3 += this.fillInBlank(yddd, 4);
				track3 += "0160000000100000";
				track3 += expiry_yymm;
				track3 += "0=0=0=0000000";
				track3 += cvv;
				track3 += "?";
				////log.debug(" branch_id :" + dbTbl.getField("CB_COLLECT_CD"));
				////log.debug(" product_code :" + dbTbl.getField("CB_CARD_PRDCT_GROUP"));
				////log.debug(" get card mode :" + dbTbl.getField("CB_CARD_COLL_METHOD"));
				////log.debug(" new reason :" + newReason.toUpperCase());
				////log.debug(" user date :" + dbTbl.getField("CB_USER_DATE_1"));
				////log.debug(" photo_ind :" + photo_ind);
				////log.debug(" photo_name :" + photo_name);				
				fos.write(this.fillInBlank(dbTbl.getField("CB_COLLECT_CD"), 6).getBytes());			 
				fos.write(this.fillInBlank(dbTbl.getField("CB_CARD_PRDCT_GROUP"), 4).getBytes());	 
				fos.write("0000".getBytes());				 
				fos.write(this.fillInBlank(dbTbl.getField("CB_CARD_COLL_METHOD"), 1).getBytes());		 
				fos.write(this.fillInBlank(newReason.toUpperCase(), 4).getBytes());			 
				fos.write(this.fillInBlank(dbTbl.getField("CB_USER_DATE_1"), 8).getBytes());		 
				////System.out.println("cardnos[]:"+cardnos[i]);
				fos.write(this.fillInBlank(cardnos[i], 28).getBytes());		 
				fos.write(expiry_yymm.getBytes());	 
				//fos.write(this.fillInBlank(dbTbl.getField("CB_EMBOSSNAME"), 24).getBytes());
				fos.write(this.fillInBlank("", 24).getBytes());//压花姓名行1
				fos.write(this.fillInBlank("", 24).getBytes());//压花姓名行2				 
				fos.write(cvv2.getBytes());					//CVV2/CVC2 3
				fos.write(photo_ind.getBytes());					 
				fos.write(this.fillInBlank(photo_name, 25).getBytes());					 
				fos.write(this.fillInBlank(track2, 40).getBytes());				//track2
				fos.write(this.fillInBlank(track3, 79).getBytes());				//track3
				fos.write(this.fillInBlank("", 50).getBytes());					 
				fos.write(this.checkAlphanumeric(strAdd1, 40).getBytes());
				fos.write(this.checkAlphanumeric(strAdd2, 40).getBytes());
				fos.write(this.checkAlphanumeric(strAdd3, 40).getBytes());
				fos.write(this.checkAlphanumeric(strAdd4, 40).getBytes());
				fos.write(this.checkAlphanumeric(strAdd5, 40).getBytes());
				fos.write(this.checkAlphanumeric(dbCstTbl.getField("CB_HOME_CITY"), 20).getBytes());
				fos.write(this.checkAlphanumeric(dbCstTbl.getField("CB_HOME_STATE"), 20).getBytes());
				fos.write(this.fillInBlank(dbCstTbl.getField("CB_HOME_CNTRY_CD"), 2).getBytes());
				fos.write(this.fillInBlank(dbCstTbl.getField("CB_HOME_POSTCODE"), 10).getBytes());
				fos.write(this.checkAlphanumeric(dbCstTbl.getField("CB_CARDHOLDER_NAME"), 60).getBytes());
				fos.write(this.fillInBlank(dbTbl.getField("CB_ID_TYPE"), 1).getBytes());
				fos.write(this.fillInBlank(dbTbl.getField("CB_IDNO"), 25).getBytes());
				fos.write("\r\n".getBytes());
				fos.flush();
				fos.close();				
				dbNewCrd.setField("NC_PICK_DATE", nextProCessingDate);
				dbNewCrd.update();
			}			
			File zipFile = new File(tempDir, subPath);	
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFile, false));	
			for (int i = 0; i < temp.size(); i++) {			
				File f = new File(tempDir ,(String) temp.get(i));
				out.putNextEntry(new ZipEntry((String) temp.get(i)));			
				FileInputStream in = new FileInputStream(f);
				int ret;
				while ((ret = in.read())!= -1){
					out.write(ret);
				}
				in.close();	
				f.delete();
				
			}
			out.close();
			
			fileNameMap.clear();
			temp.clear();

			//hsm.closeHSM();
			
			TransactionMgr.commit();
		
			//加载PGP公钥
			PGPPublicKey encKey = PGPKeyUtil.readPublicKey(ConfigManager.getConfigDir() + "/em_pgp_public.key");
			File pgpFile = new File(tempDir, zipFile.getName() + ".pgp");
			pgpEncrypt(zipFile,  FileUtils.openOutputStream(pgpFile), encKey, false, true);
		} catch (FileNotFoundException e) {
			log.error("FileNotFoundException", e);
		} catch (IOException e) {
			log.error("IOException", e);
		}catch (Exception e) {
			log.error("Exception", e);
			TransactionMgr.rollback();
			throw e;
		} finally {
			hsm.closeHSM();		// add by wangting, 20070914, for long activity socket in one batch transaction
			TransactionMgr.closeSession();
		}
		return subPath + ".pgp";
	}
	
	public String getEmbossingFileCallC(String realPath, String filename){
		String subPath =filename+".zip";
		return subPath;
	}
	
	public String fillInBlank(String str, int length){
		
		String retStr = str;
		
		int i = retStr.length();
		
		if(i<length){
			for(int k=0;k<length-i;k++){
				retStr += " ";
			}
		}else{
			retStr = retStr.substring(0, length);
		}
		
		return retStr;
	}
	
	public String checkAlphanumeric(String str, int length){
		String retStr = str;
		int str_len = str.length();
		for (int i = 0; i < str.length(); i++) {
			char c=str.charAt(i);
			if(Pattern.matches("[\u4e00-\u9fa5]", String.valueOf(c))){
				str_len ++ ;
				////log.debug(this.getClass()+"character["+c+"] is Chinese");
			}
		}
		for (int j = 0; j < length-str_len; j++) {
			retStr += " ";
		}
		return retStr;
	}

	//@SuppressWarnings("static-access")
	protected String _activeCard(String cardno) throws Exception {
		DBConnection myConnection = TransactionMgr.currentSession();

		boolean notAllowed = false;
		OB_CP_CRDTBL obTbl = new OB_CP_CRDTBL();
		obTbl.CB_CARDHOLDER_NO(cardno);
		DB_CP_CRDTBL dbTbl = new DB_CP_CRDTBL(myConnection, user);
		boolean foundCard = dbTbl.doFind(obTbl);
		if (!foundCard) {
			////log.info("card not found, card number:" + cardno);
			return "CCAM01032.CARD_NOT_FOUND";
		} else {
			if (obTbl.CB_NEW_PLASTIC_CD().length() <= 0) {
				////log.info("card activated:" + cardno);
				return ("CCAM01032.CARD_PREVIOUSLY_ACTIVATED");
			} else if (obTbl.CB_NEW_PLASTIC_CD().equals("D")) {
				////log.info("card destoryed" + cardno);
				return ("CCAM01032.CARD_DESTROYED");
			} else {
				if (obTbl.CB_CORPORATE_ID().trim().length() > 0) {
					OB_CP_COPMST obMst = new OB_CP_COPMST();
					obMst.CO_CORPORATE_ID(obTbl.CB_CORPORATE_ID());
					DB_CP_COPMST dbMst = new DB_CP_COPMST(myConnection, user);
					boolean foundMst = dbMst.doFindByKey(obMst);
					if (foundMst) {
						if (cardno.equals(obMst.CO_BILL_CARDHOLDER_NO())) {
							notAllowed = true;
							return ("CCAM01032.Maintainance_of_Corporate_Billing_Account_Not_Allowed");
						}
					}
				}
				if (!notAllowed) {
					Date sysDate = new Date();
					com.coshine.ccs.function.DateUtil d1 = new com.coshine.ccs.function.DateUtil();
					String ThesysDate = d1.getStringForDate(sysDate, "yyyyMMdd");
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd.kk.mm.ss.SSSSSS");
					OB_CP_PLSTIC obPls = new OB_CP_PLSTIC();
					obPls.PC_CARDHOLDER_NO(cardno);
					obPls.PC_CARDHOLDER_NAME(obTbl.CB_EMBOSSNAME());
					obPls.PC_OPLASTIC_CD(obTbl.CB_PLASTIC_CD());
					obPls.PC_NPLASTIC_CD(" ");
					obPls.PC_OPLASTIC_DATE(obTbl.CB_PLASTIC_DATE());
					obPls.PC_NPLASTIC_DATE(ThesysDate);
					obPls.PC_ACTION("A");
					obPls.PC_OFF_ID(user);
					obPls.PC_TIMESTAMP(format.format(sysDate));
					DB_CP_PLSTIC dbPls = new DB_CP_PLSTIC(myConnection, user);
					dbPls.doAdd(obPls);
					/*
					 * String oldExpDate = null;
					 * 
					 * oldExpDate = ob.getOldExpDate();
					 */

					// update cp_crdtbl
					OB_CP_CRDTBL obTblUp = new OB_CP_CRDTBL();
					DB_CP_CRDTBL dbTblUp = new DB_CP_CRDTBL(myConnection, user);
					obTblUp.CB_CARDHOLDER_NO(cardno);
					obTblUp.CB_PLASTIC_CD(" ");
					obTblUp.CB_NEW_PLASTIC_CD(" ");
					obTblUp.CB_PLASTIC_DATE(ThesysDate);
					obTblUp.CB_NEW_PLASTIC_DATE("00000000");
					obTblUp.CB_EXPIRY_CCYYMM(obTbl.CB_NEW_EXPIRY_CCYYMM());
					obTblUp.CB_NEW_EXPIRY_CCYYMM("000000");
					obTblUp.CB_4DBC(obTbl.CB_NEW_4DBC());

					// check against cp_crdlnk || cp_replcn
					OB_CP_CRDLNK obLNK = new OB_CP_CRDLNK();
					DB_CP_CRDLNK dbLNK = new DB_CP_CRDLNK(myConnection, user);
					obLNK.CL_NEW_CARD(cardno);
					boolean foundLNK = dbLNK.doFindByKey(obLNK);

					OB_CP_REPLCN obRep = new OB_CP_REPLCN();
					DB_CP_REPLCN dbRep = new DB_CP_REPLCN(myConnection, user);
					obRep.RN_NEW_CARDHOLDER_NO(cardno);
					boolean foundREP = dbRep.doFindByKey(obRep);

					if (foundLNK || foundREP) {
						obTblUp.CB_CAF_TAG_FLAG("U");
					} /*
						 * else
						 * if(!oldExpDate.equals(obTbl.CB_EXPIRY_CCYYMM())){
						 * obTblUp.CB_CAF_UPDATE_FLAG("U"); }
						 */
					obTblUp.CB_CAF_UPDATE_FLAG("U");
					if (!obTbl.CB_CIF_FLAG().equals("D")|| !obTbl.CB_CIF_FLAG().equals("C")) {
						obTblUp.CB_CIF_FLAG("U");
					}
					dbTblUp.doUpdate(obTblUp);
					_updateLnk(cardno);
				}
			}
		}
		return null;
	}

	private void _updateLnk(String cardNo) throws DBException {
		DBConnection myConnection = TransactionMgr.currentSession();
		try {
			OB_CP_CRDLNK obLnk = new OB_CP_CRDLNK();
			obLnk.CL_NEW_CARD(cardNo);
			DB_CP_CRDLNK dbLnk = new DB_CP_CRDLNK(myConnection, user);
			boolean foundInLnk = dbLnk.doFind(obLnk);
			if (foundInLnk) {
				if (obLnk.CL_ACTIVE_FLAG().equals("Y")) {
					obLnk.CL_ACTIVE_FLAG("N");
					dbLnk.doUpdate(obLnk);
				}
			}
		} catch (Exception ex) {
			log.error("Problem update old card table", ex);

		}
	}
	
	private void pgpEncrypt(File sourceFile, OutputStream targetOut, 
	        PGPPublicKey encKey, boolean armor, boolean withIntegrityCheck)
	        throws IOException, NoSuchProviderException, PGPException {
			
    	Security.addProvider(new BouncyCastleProvider());
    	OutputStream tOut = null;
    	OutputStream cOut = null;
    	InputStream bIn = null;
    	try {
	        if (armor) {
	        	targetOut = new ArmoredOutputStream(targetOut);
	        }
	        
	        File tmpFile = File.createTempFile(System.currentTimeMillis() + ".", "pgp");
	        tOut = new FileOutputStream(tmpFile);

	        PGPCompressedDataGenerator zipCompress = new PGPCompressedDataGenerator(PGPCompressedData.ZIP);
	        PGPUtil.writeFileToLiteralData(zipCompress.open(tOut), PGPLiteralData.BINARY, sourceFile);
	        zipCompress.close();
	        IOUtils.closeQuietly(tOut);
	        
	        BcPGPDataEncryptorBuilder builder = new BcPGPDataEncryptorBuilder(PGPEncryptedData.CAST5);
	        builder.setWithIntegrityPacket(withIntegrityCheck);
	        PGPEncryptedDataGenerator generator = new PGPEncryptedDataGenerator(builder);
	        generator.addMethod(new BcPublicKeyKeyEncryptionMethodGenerator(encKey));

	        cOut = generator.open(targetOut, tmpFile.length());
	        bIn = new FileInputStream(tmpFile);
	        IOUtils.copy(bIn, cOut);
    	} finally {
    		IOUtils.closeQuietly(cOut);
    		IOUtils.closeQuietly(targetOut);
    		IOUtils.closeQuietly(bIn);
    	}
    }
}
