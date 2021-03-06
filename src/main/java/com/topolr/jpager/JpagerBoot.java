package com.topolr.jpager;

import com.topolr.boot.IBootService;
import com.topolr.jpager.base.Jpager;
import com.topolr.util.jsonx.Jsonx;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JpagerBoot implements IBootService {

    @Override
    public void serviceStart(Jsonx json) {
        try {
            Jpager.init(json);
            JpagePolice.getPolice().startup();
        } catch (Exception ex) {
            Logger.getLogger(JpagerBoot.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void serviceStop() {
        try {
            JpagePolice.getPolice().stupdown();
            JpageContainer.getContainer().backup();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
