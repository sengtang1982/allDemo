package com.ebitg.lang;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class InvokeCSharp {
	public static void main(String[] args) {
		ActiveXComponent dotnetCom = new ActiveXComponent("InvokeCSharp.ExampleClass");
		Variant var = Dispatch.call(dotnetCom, "HelloJava", "Jacob真是个好东东");
		String str = var.toString();
		System.out.println(str);
	}
}
