package ibatis;



import com.bierbobo.rainbow.data.orm.mybatis.domain.SubscibeTask;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lifubo on 2016/10/28.
 */
@Repository
public interface SubscibeTaskDao {

    List<SubscibeTask> querySubscibeTaskList(SubscibeTask subscibeTask);

    SubscibeTask querySubscibeTask(SubscibeTask subscibeTask);

    void insertSubscibeTask(SubscibeTask subscibeTask);

    int updateSubscibeTask(SubscibeTask subscibeTask);

    int deleteSubscibeTask(SubscibeTask subscibeTask);

    List<SubscibeTask> queryNextExecSubscibeTaskList(SubscibeTask subscibeTask);

    List<SubscibeTask> updateTaskStateAndMsg(List<SubscibeTask> records);
}
