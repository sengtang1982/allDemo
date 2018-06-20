package com.ebitg.commons.dbcp;

import java.sql.ResultSet;

public interface MyDbCallback {

	void execute(ResultSet rs);

}
