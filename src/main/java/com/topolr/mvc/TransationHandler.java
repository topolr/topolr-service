package com.topolr.mvc;

import com.topolr.mvc.ConnectionManager.BaseConnection;
import com.topolr.mvc.annotation.Dao;
import com.topolr.mvc.annotation.Transation;
import com.topolr.mvc.mapping.DaoContainer;
import com.topolr.util.base.reflect.BaseProxy;
import com.topolr.util.base.reflect.ObjectSnooper;
import com.topolr.util.base.reflect.each.JfieldEach;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class TransationHandler extends BaseProxy {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        SessionState sessionState = this.getSessionState();
        Object result = null;
        ObjectSnooper.snoop(this.agent).fields(new JfieldEach(this.agent) {
            @Override
            public boolean each(Field method) throws Exception {
                Object xt = arguments[0];
                if (method.isAnnotationPresent(Dao.class)) {
                    Object a = DaoContainer.getCotnainer().getDao(method.getAnnotation(Dao.class).name());
                    try {
                        method.setAccessible(true);
                        method.set(xt, a);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return false;
            }
        });
        Method originalMethod = this.agent.getClass().getMethod(method.getName(), method.getParameterTypes());
        boolean action = true;
        if (originalMethod.isAnnotationPresent(Transation.class)) {
            if (sessionState.length() == 0) {
                sessionState.push();
                action = false;
            }
            BaseConnection connetion = sessionState.getConnectionManager().getConnection();
            try {
                connetion.setAutoCommit(false);
                result = method.invoke(this.agent, args);
                connetion.commit();
            } catch (Exception e) {
                connetion.rollback();
                throw e;
            } finally {
                connetion.close();
                if (action == false) {
                    sessionState.pop();
                }
            }
        } else {
            sessionState.push();
            BaseConnection connetion = sessionState.getConnectionManager().getConnection();
            connetion.setAutoCommitTrue();
            try {
                result = method.invoke(this.agent, args);
            } catch (Exception e) {
//                e.printStackTrace();
                throw e;
            } finally {
                connetion.close();
                sessionState.pop();
            }
        }
        return result;
    }

    private SessionState getSessionState() {
        if (null == ControlCenter.sessionState.get()) {
            SessionState state = new SessionState();
            ControlCenter.sessionState.set(state);
        }
        return (SessionState) ControlCenter.sessionState.get();
    }
}
