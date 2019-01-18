package biodiv.dataTable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.hibernate.SessionFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import biodiv.Transactional;
import biodiv.activityFeed.ActivityFeedService;
import biodiv.comment.CommentService;
import biodiv.common.AbstractService;
import biodiv.observation.RecommendationVote;
import biodiv.observation.RecommendationVoteDao;
import biodiv.user.UserService;

public class DataTableService extends AbstractService<DataTable> {

    private final Logger log = LoggerFactory.getLogger(getClass());
    @Inject
    SessionFactory sessionFactory;
    private DataTableDao dataTableDao;
    @Inject
    private UserService userService;
    @Inject
    private ActivityFeedService activityFeedService;
    @Inject
    private CommentService commentService;

    @Inject
    DataTableService(DataTableDao dataTableDao) {
        super(dataTableDao);
        this.dataTableDao = dataTableDao;
    }

    @Transactional
    public List<List<String>> fetchColumnNames(DataTable dataTable) {
        List<List<String>> res = new ArrayList<List<String>>();
        String dtColumns = dataTable.getColumns();
        if (dtColumns != null) {
            JSONArray tColumns = new JSONArray(dtColumns);
            Iterator tColumnsIt = tColumns.iterator();
            while (tColumnsIt.hasNext()) {
                JSONArray tRow = (JSONArray) tColumnsIt.next();
                List cFieldList = new ArrayList();
                Iterator tRowIt = tRow.iterator();
                while (tRowIt.hasNext()) {
                    cFieldList.add(tRowIt.next());
                }
                res.add(cFieldList);
            }
        }
        return res;
    }
}
