package weihuagu.com.jian.model;
import android.content.Context;

public class RuntimeSetting implements ISetting {

	private static RuntimeSetting runtimesetting = null;
	private Context context = null;

	private RuntimeSetting() {
	}

	public static void setInstance(Context context) {
			runtimesetting = new RuntimeSetting();

	}
	public static RuntimeSetting getInstance(){
		if(runtimesetting!=null){
			return runtimesetting;
		}else {
			return null;
		}

	}
}



