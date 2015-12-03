package ua.ck.civilscouncil.activities;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONObject;

import ua.ck.civilscouncil.R;
import ua.ck.civilscouncil.entities.Project;
import ua.ck.civilscouncil.utils.Const;

/**
 * Created by cheb on 10/3/15.
 */
public class ProjectDetailsActivity extends Activity {

    private AQuery aq;
    private long mProjectId;
    private Project mCurrentProject;
    private ImageView ivProjectImage;
    private TextView tvProjectDescription;
    private TextView tvProjectTitle;
    private Button btnLike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_details);

        aq = new AQuery(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.containsKey("id"))
                mProjectId = extras.getLong("id");
        }

        ivProjectImage = (ImageView) findViewById(R.id.projectImage);
        tvProjectTitle = (TextView) findViewById(R.id.projectTitle);
        tvProjectDescription = (TextView) findViewById(R.id.projectDescription);
        btnLike = (Button) findViewById(R.id.btn_vote);

        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aq.ajax(Const.getProjectLikeURL(mProjectId), JSONObject.class, new AjaxCallback<JSONObject>() {
                    @Override
                    public void callback(String url, JSONObject object, AjaxStatus status) {

                    }
                });
            }
        });

        getProjectDetails();
    }

    private void getProjectDetails() {
        aq.ajax(Const.getProjectURL(mProjectId), JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject object, AjaxStatus status) {
                try {
                    mCurrentProject = new Project(object);
                    refreshProjectDetails();
                } catch (Exception ex) {

                }
            }
        });
    }

    private void refreshProjectDetails() {
        if (mCurrentProject != null) {
            aq.id(ivProjectImage).image(mCurrentProject.getPicture());
            tvProjectTitle.setText(mCurrentProject.getTitle());
            tvProjectDescription.setText(mCurrentProject.getDescription());
        }
    }
}
