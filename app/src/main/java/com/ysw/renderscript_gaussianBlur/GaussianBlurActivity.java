package com.ysw.renderscript_gaussianBlur;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.ysw.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Swy on 2017/7/6.
 */

public class GaussianBlurActivity extends AppCompatActivity {

    @Bind(R.id.container)
    ImageView container;
    @Bind(R.id.imageView)
    ImageView imageView;
    @Bind(R.id.seekBar)
    SeekBar seekBar;
    @Bind(R.id.textViewProgress)
    TextView textViewProgress;
    @Bind(R.id.textViewDialog)
    TextView textViewDialog;
    @Bind(R.id.layout)
    LinearLayout layout;

    private RenderScriptGaussianBlur renderScriptGaussianBlur;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gaussianblur);
        ButterKnife.bind(this);
        container.setVisibility(View.GONE);
        layout.setVisibility(View.VISIBLE);
        renderScriptGaussianBlur=new RenderScriptGaussianBlur(this);
        //模糊半径(radius)越大，性能要求越高，模糊半径不能超过25，所以并不能得到模糊度非常高的图片。
        seekBar.setMax(25);//设置进度条的最大值为25
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textViewProgress.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int radiu=seekBar.getProgress();
                if (radiu<1){
                    radiu=1;
                }
                Bitmap bitmap= BitmapFactory.decodeResource(getResources(), R.drawable.image);
                imageView.setImageBitmap(renderScriptGaussianBlur.gaussianBlur(radiu,bitmap));
            }
        });

        textViewDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                container.setVisibility(View.VISIBLE);
                layout.setDrawingCacheEnabled(true);
                layout.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_LOW);
                Bitmap bitmap = layout.getDrawingCache();

                container.setImageBitmap(renderScriptGaussianBlur.gaussianBlur(25, bitmap));
                layout.setVisibility(View.INVISIBLE);
                AlertDialog dialog=new AlertDialog.Builder(GaussianBlurActivity.this).create();
                dialog.setTitle("GaussianBlur");
                dialog.setMessage("GaussianBlur");
                dialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        container.setVisibility(View.GONE);
                        layout.setVisibility(View.VISIBLE);
                    }
                });
                dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        container.setVisibility(View.GONE);
                        layout.setVisibility(View.VISIBLE);
                    }
                });
                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        container.setVisibility(View.GONE);
                        layout.setVisibility(View.VISIBLE);
                    }
                });
                dialog.show();
            }
        });
    }
}
