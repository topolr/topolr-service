package com.topolr.boot;

import com.topolr.util.jsonx.Jsonx;

public interface IBootService {

	public void serviceStart(Jsonx option);

	public void serviceStop();
}