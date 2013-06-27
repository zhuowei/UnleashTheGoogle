package net.zhuoweizhang.unleashthegoogle;

import java.io.*;
import java.util.*;

import android.app.*;
import android.content.*;
import android.os.Bundle;
import android.widget.*;

import eu.chainfire.libsuperuser.Shell;

public class MainActivity extends Activity
{
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		try {
			runUnleash();
			Toast.makeText(this, "Enabled debug options, hopefully. Force-close Google Search to see results", Toast.LENGTH_LONG).show();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finish();
	}

	public void runUnleash() throws Exception {
		Context searchContext = createPackageContext("com.google.android.googlequicksearchbox", Context.CONTEXT_IGNORE_SECURITY);
		File settingsFile = new File(searchContext.getDir("shared_prefs", 0), "StartupSettings.bin");
		String cmdLine = "dalvikvm -Djava.library.path=" + System.getProperty("java.library.path") + " -classpath " + 
			getPackageCodePath() + " net.zhuoweizhang.unleashthegoogle.UnleashMain " + settingsFile.getAbsolutePath() + " 2";
		String commandLine = cmdLine.toString();
		System.out.println(commandLine);

		List<String> suResult = Shell.SU.run(commandLine);
		System.out.println(suResult);
	}
}
