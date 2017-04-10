package com.topolr.mvc.data.dao;

import com.topolr.mvc.ConnectionManager.BaseConnection;
import com.topolr.mvc.ControlCenter;
import com.topolr.mvc.SessionState;
import java.sql.SQLException;

public abstract class BaseDao {
    protected BaseConnection getConnection() throws SQLException{
        if (null == ControlCenter.sessionState.get()) {
            SessionState state = new SessionState();
            state.push();
            ControlCenter.sessionState.set(state);
        }
        return ((SessionState)ControlCenter.sessionState.get()).getConnectionManager().getConnection();
    };
}
