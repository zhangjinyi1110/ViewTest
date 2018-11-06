package com.project.viewtest.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.viewtest.R;
import com.project.viewtest.widget.WaveView;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity {

    private final String TAG = TestActivity.class.getSimpleName();
    private List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        list = new ArrayList<>();
        initList();
        RecyclerView recyclerView = findViewById(R.id.test_recycler);
        recyclerView.addItemDecoration(new MyItemDecoration());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter(list));
        final WaveView waveView = findViewById(R.id.wave);
        waveView.post(new Runnable() {
            @Override
            public void run() {
                waveView.startAnim();
            }
        });
    }

    private void initList() {
        for (int i = 0; i < 50; i++) {
            list.add("这是第" + (i + 1) + "项");
        }
    }

    private class MyItemDecoration extends RecyclerView.ItemDecoration {

        Bitmap bitmap;

        public MyItemDecoration() {
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon5);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            Log.i(TAG, "getItemOffsets: ");
            outRect.left = 100;
            outRect.bottom = 30;
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDraw(c, parent, state);
            Log.i(TAG, "onDraw: ");
            RecyclerView.LayoutManager manager = parent.getLayoutManager();
            Paint paint = new Paint();
            paint.setColor(Color.BLUE);
            paint.setStrokeWidth(10);
            paint.setStyle(Paint.Style.STROKE);
            Paint cPaint = new Paint();
            cPaint.setColor(Color.BLUE);
            int radius = 25;
            for (int i = 0; i < parent.getChildCount(); i++) {
                View view = parent.getChildAt(i);
                int x = manager.getLeftDecorationWidth(view) / 2;
                int h = view.getTop() + view.getHeight() / 2;
                c.drawCircle(x, h, radius, paint);
                c.drawCircle(x, h, radius / 3, cPaint);
                if (i != 0)
                    c.drawLine(x, view.getTop() + manager.getTopDecorationHeight(view), x, h - radius - 10, paint);
                if (i != parent.getChildCount() - 1)
                    c.drawLine(x, view.getBottom() + manager.getBottomDecorationHeight(view), x, h + radius + 10, paint);
            }
        }

    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        private List<String> list;

        public MyAdapter(List<String> list) {
            this.list = list;
        }

        @NonNull
        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.test_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {
            holder.textView.setText(list.get(position));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView textView;

            public ViewHolder(View itemView) {
                super(itemView);
                textView = itemView.findViewById(R.id.item_test);
            }
        }
    }
}
