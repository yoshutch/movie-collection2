package hutchtech.movies.domain;

import com.fasterxml.jackson.annotation.JsonValue;


/**
 * Created by yoshutch on 7/24/16.
 */
public class Medium implements Comparable<Medium>{
	public static final Medium DVD = new Medium("DVD");
	public static final Medium BLURAY = new Medium( "Blu-ray");
	public static final Medium VHS = new Medium("VHS");
	public static final Medium AMAZON = new Medium("Amazon");
	public static final Medium ULTRA_VIOLET = new Medium("UltraViolet");
	public static final Medium GOOGLE_PLAY = new Medium("Google Play");
	public static final Medium DIGITAL = new Medium("Digital");

	private String val;
	private String note;

	public Medium (String val){
		this.val = val;
	}

	public Medium(String val, String note) {
		this.val = val;
		this.note = note;
	}

	@JsonValue
	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Override
	public String toString() {
		return "Medium{" +
				"val='" + val + '\'' +
				", note='" + note + '\'' +
				'}';
	}

	@Override
	public int compareTo(Medium m2) {
		return this.val.compareTo(m2.getVal());
	}


}
