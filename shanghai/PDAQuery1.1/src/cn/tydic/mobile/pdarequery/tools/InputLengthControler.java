package cn.tydic.mobile.pdarequery.tools;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

/***
 * EditText 输入字数限制
 * @author Administrator
 *
 */
public class InputLengthControler {
	private EditText mEditText;
	private final String hint_max_length_msg = "最多只能输入%s个字符。";
	private int MAX_LENGTH = 10;

	public void config(EditText inputBox, int maxLength) {
		MAX_LENGTH = maxLength;
		mEditText = inputBox;
		mEditText.addTextChangedListener(watcher);
	}
	private TextWatcher watcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {

		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {

		}

		@Override
		public void afterTextChanged(Editable s) {
			if (mEditText.getText().length() > MAX_LENGTH) {
				String str = mEditText.getText().toString().trim();
				if (str != null && str.length() > MAX_LENGTH) {
					str = str.substring(0, MAX_LENGTH);
					mEditText.setText(str);
					mEditText.setSelection(str.length());
				}
				toast(String.format(hint_max_length_msg, MAX_LENGTH));
			}
			int enableCount = MAX_LENGTH;
			Editable curContent = mEditText.getText();
			if (curContent != null && curContent.length() > 0) {
				enableCount = MAX_LENGTH - curContent.length();
			}
		}
	};
	private void toast(String msg) {
		Context context;
		if (msg != null && (context = getContext()) != null) {
			Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
		}
	}

	private Context getContext() {
		if (mEditText != null) {
			return mEditText.getContext();
		}
		return null;
	}

}
