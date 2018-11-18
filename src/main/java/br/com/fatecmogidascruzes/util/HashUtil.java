package br.com.fatecmogidascruzes.util;

public class HashUtil {

	public static boolean validateAppHash(String appHash) {
		return ("KSMRERN_OPD_2018_BSOFSTVRTYP".equals(appHash) || "KSMRERN_SMFTPOF_2018_BSOFSTVRTYP".equals(appHash));
	}

}
