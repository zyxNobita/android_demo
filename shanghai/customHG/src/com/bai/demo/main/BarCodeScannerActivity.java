package com.bai.demo.main;

import com.bai.demo.R;
import com.covics.zxingscanner.OnDecodeCompletionListener;
import com.covics.zxingscanner.ScannerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.TextView;

public class BarCodeScannerActivity extends Activity implements OnDecodeCompletionListener {
	
	private ScannerView scannerView;
	private TextView txtResult;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.api_camera_scanner);
        
		scannerView = (ScannerView) findViewById(R.id.scanner_view);
		txtResult = (TextView) findViewById(R.id.txtResult);
		scannerView.setOnDecodeListener(this);
    }

	public void onDecodeCompletion(String barcodeFormat, String barcode, Bitmap bitmap) {
		// TODO Auto-generated method stub
		txtResult.setText("条码制式:" + barcodeFormat + "条码:" + barcode);
		Intent mIntent = getIntent();
		
		mIntent.putExtra("num", barcode);
		setResult(RESULT_OK, mIntent);
		this.finish();		
	}

	@Override
	protected void onResume() {
		super.onResume();
		scannerView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		scannerView.onPause();

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}