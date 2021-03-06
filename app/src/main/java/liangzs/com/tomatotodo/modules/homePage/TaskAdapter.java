package liangzs.com.tomatotodo.modules.homePage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import liangzs.com.tomatotodo.R;
import liangzs.com.tomatotodo.data.entity.Task;

import static liangzs.com.tomatotodo.modules.homePage.MainPresenter.NONE;
import static liangzs.com.tomatotodo.modules.homePage.MainPresenter.REST;
import static liangzs.com.tomatotodo.modules.homePage.MainPresenter.WORK;

/**
 * @author liangzs
 * @Date 2019/1/14
 */
public class TaskAdapter extends BaseAdapter {
    private ItemListener itemListener;
    private boolean isModifySeq;

    public TaskAdapter(List<Task> data, ItemListener itemListener) {
        this.itemListener = itemListener;
        this.data = data;
    }


    private List<Task> data;

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Task getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rootView = convertView;
        if (rootView == null) {
            LayoutInflater flater = LayoutInflater.from(parent.getContext());
            rootView = flater.inflate(R.layout.task_list_item, parent, false);
        }
        final Task task = data.get(position);
        TextView textView = rootView.findViewById(R.id.item_title);
        ImageView imageView = rootView.findViewById(R.id.item_play);
        textView.setText(task.getTitle());
        if (isModifySeq) {
            rootView.findViewById(R.id.move_item).setVisibility(View.VISIBLE);
            imageView.setVisibility(View.GONE);
        } else {
            rootView.findViewById(R.id.move_item).setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);
        }
        if (WORK.equals(task.getCloclType())) {
            imageView.setImageResource(R.mipmap.ic_media_pause);
        } else if (REST.equals(task.getCloclType())) {
            imageView.setImageResource(R.mipmap.ic_checkmark_light);
        } else {
            imageView.setImageResource(R.mipmap.ic_media_play);
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemListener.playOnclick(task, position);
            }
        });

        return rootView;
    }

    public void setData(List<Task> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void modifySeq(boolean flag) {
        isModifySeq = flag;
        notifyDataSetChanged();
    }

    /**
     * 更新任务状态
     *
     * @param task
     */
    public void changePlayStatus(Task task) {
        for (Task t : data) {
            if (task.getId() == t.getId()) {
                t = task;
                break;
            }
        }
        notifyDataSetChanged();
    }

    /**
     * 交换位置
     *
     * @param start
     * @param end
     */
    public void change(int start, int end) {
        Task srcData = data.get(start);
        data.remove(srcData);
        data.add(end, srcData);
        notifyDataSetChanged();
    }

    /**
     * 删除数据项
     *
     * @param task
     */
    public void deleteTask(Task task) {
        data.remove(task);
        notifyDataSetChanged();

    }

    public boolean isModifySeq() {
        return isModifySeq;
    }

    public interface ItemListener {
        void playOnclick(Task task, int position);
    }
}
