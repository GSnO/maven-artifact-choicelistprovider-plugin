package com.wincornixdorf.jenkins.plugins.jenkins_extensible_choice_plugin.maven_artifact;

import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class ValidAndInvalidClassifier {

	public static final String DEFAULT_ACCEPTED_CLASSIFIER = "*";

	final Set<String> mValid;
	final Set<String> mInvalid;
	boolean mValidByDefault;

	public ValidAndInvalidClassifier() {
		this(true);
	}

	public ValidAndInvalidClassifier(final boolean pValidByDefault) {
		mValid = new HashSet<String>();
		mInvalid = new HashSet<String>();
		mValidByDefault = pValidByDefault;
	}

	public boolean addInvalid(String e) {
		return mInvalid.add(e);
	}

	public boolean addValid(String e) {
		// we dont want to be valid by default, only for explicit types
		mValidByDefault = false;
		return mValid.add(e);
	}

	public boolean isInvalid(String e) {
		return mInvalid.contains(e);
	}

	public boolean isValid(String e) {
		return mValidByDefault || mValid.contains(e);
	}

	public Set<String> getValid() {
		return mValid;
	}

	public Set<String> getInvalid() {
		return mInvalid;
	}

	public static ValidAndInvalidClassifier getDefault() {
		return new ValidAndInvalidClassifier(true);
	}

	public static ValidAndInvalidClassifier fromString(final String pClassifier) {
		ValidAndInvalidClassifier retVal = new ValidAndInvalidClassifier();
		if (pClassifier != null && !"".equals(pClassifier)) {
			StringTokenizer tokenizer = new StringTokenizer(pClassifier, ",");
			while (tokenizer.hasMoreTokens()) {
				String current = tokenizer.nextToken();
				if (current.startsWith("!")) {
					// cut of the "!"
					retVal.addInvalid(current.substring(1));
				} else {
					retVal.addValid(current);
				}
			}
		}
		return retVal;
	}
}