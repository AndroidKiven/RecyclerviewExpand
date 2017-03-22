package shintellilink.recyclerviewexpand;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 实例化控件
        final RecyclerView rv = (RecyclerView) findViewById(R.id.rv);

        // 设置启动列表的修改动画效果(默认为关闭状态) 23.0.1以后使用方法，23.0.1之前用setSupportsChangeAnimations方法
        RecyclerView.ItemAnimator animator = rv.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }
        // 设置动画时长
        rv.getItemAnimator().setChangeDuration(300);
        rv.getItemAnimator().setMoveDuration(300);

        // 实现RecyclerView实现竖向列表展示模式
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);

        // 实例化数据适配器并绑定在控件上
        final MainAdapter adapter = new MainAdapter();
        rv.setAdapter(adapter);
    }

    public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {

        // 为列表提供数据的数据集合
        final String[] contacts = new String[]{
                "Java",
                "Android",
                "C",
                "PHP",
                "C++",
                "C#",
                "Spark",
                "Hadoop",
                "JS",
                "Redis",
                "GO",
                "OC",
                "Swifit",
                "Kolin",
                "Sketch",
                "Python",
                "Shell"
        };

        // 列表展开标识
        int opened = -1;

        /**
         * 绑定item布局
         *
         * @param parent
         * @param pos
         * @return
         */
        @Override
        public MainViewHolder onCreateViewHolder(ViewGroup parent, int pos) {
            return new MainViewHolder((ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false));
        }

        /**
         * 绑定数据到控件
         *
         * @param holder
         * @param pos
         */
        @Override
        public void onBindViewHolder(MainViewHolder holder, int pos) {
            final String contact = contacts[pos];
            holder.bind(pos, contact);
        }

        /**
         * 返回列表条数
         *
         * @return
         */
        @Override
        public int getItemCount() {
            return contacts.length;
        }

        /**
         * 实例化控件等操作
         */
        public class MainViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            // 标题
            public final TextView contactNameTV;
            // 隐藏的内容
            public final TextView infos;

            // 实例化
            public MainViewHolder(ViewGroup itemView) {
                super(itemView);
                contactNameTV = ((TextView) itemView.findViewById(R.id.contactName));
                infos = ((TextView) itemView.findViewById(R.id.infos));

                itemView.setOnClickListener(this);
            }

            // 此方法实现列表的展开和关闭
            public void bind(int pos, String name) {
                contactNameTV.setText(name);

                if (pos == opened)
                    infos.setVisibility(View.VISIBLE);
                else
                    infos.setVisibility(View.GONE);

            }

            /**
             * 为item添加点击效果,根据业务需求实现不同的效果
             * (recyclerView是不提供onItemClickListener的。所以列表的点击事件需要我们自己来实现)
             *
             * @param v
             */
            @Override
            public void onClick(View v) {
                if (opened == getLayoutPosition()) {
                    opened = -1;
                    notifyItemChanged(getLayoutPosition());
                } else {
                    int oldOpened = opened;
                    opened = getLayoutPosition();
                    notifyItemChanged(oldOpened);
                    notifyItemChanged(opened);
                }
            }
        }
    }
}
