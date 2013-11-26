package com.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myview.R;

public class myView extends FrameLayout {

	private TextView text;

	private ImageView image;

	public myView(Context context) {
		
		this(context, null);
	}

	public myView(Context context, AttributeSet attrs) {
		
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.my_view, this, true);
		text = (TextView) findViewById(R.id.text);
		image = (ImageView) findViewById(R.id.image);
//		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.xulong_view);
//		setText(typedArray.getString(R.styleable.xulong_view_text));
	}

	//没什么用到View底层都是defStyle= 0
	public myView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void setText(String tex) {
		text.setText(tex);
	}

	public void setImage(int imag) {
		image.setBackgroundResource(imag);
	}
	
	public void setImage(Bitmap imag) {
		image.setImageBitmap(imag);
	}
}
