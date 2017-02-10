/*
 * Created By WeihuaGu (email:weihuagu_work@163.com)
 * Copyright (c) 2017
 * All right reserved.
 */

package weihuagu.com.jian;

/**
 * Created by root on 17-2-10.
 */
import android.os.Bundle;
import android.widget.Toast;
import com.kenumir.materialsettings.MaterialSettings;
import com.kenumir.materialsettings.items.HeaderItem;
import com.kenumir.materialsettings.items.CheckboxItem;
import com.kenumir.materialsettings.storage.StorageInterface;
import com.kenumir.materialsettings.storage.PreferencesStorageInterface;
public class SettingsActivity extends MaterialSettings {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_material_settings);
        addItem(new HeaderItem(this).setTitle("Sample title 1"));
        addItem(new CheckboxItem(this, "key1").setTitle("Checkbox item 1").setSubtitle("Subtitle text 1").setOnCheckedChangeListener(new CheckboxItem.OnCheckedChangeListener() {
            @Override
            public void onCheckedChange(CheckboxItem cbi, boolean isChecked) {
                Toast.makeText(SettingsActivity.this, "CHECKED: " + isChecked, Toast.LENGTH_SHORT).show();
            }
        }));
    }
    @Override
    public StorageInterface initStorageInterface() {
        return new PreferencesStorageInterface(this);
    }


}
