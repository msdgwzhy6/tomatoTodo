package liangzs.com.tomatotodo.modules.addTask;

import liangzs.com.tomatotodo.base.BasePresenter;
import liangzs.com.tomatotodo.common.GlobalApplication;
import liangzs.com.tomatotodo.data.entity.Task;

/**
 * @author liangzs
 * @Date 2019/1/14
 */
public class AddEditTaskPresenter extends BasePresenter<AddEditTaskContract.View> implements AddEditTaskContract.Presenter {
    @Override
    public void submitTask(boolean isEdit, Task task) {
        if (isEdit) {
            GlobalApplication.getInstance().getSession().getTaskDao().update(task);
        } else {
            GlobalApplication.getInstance().getSession().getTaskDao().insert(task);
        }
    }
}
