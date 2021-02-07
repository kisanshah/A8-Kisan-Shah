package com.example.resumebuilder.views;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.resumebuilder.R;
import com.example.resumebuilder.Utils;
import com.google.android.material.snackbar.Snackbar;
import com.itextpdf.text.DocumentException;

import java.io.FileNotFoundException;
import java.io.IOException;

public class EditActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout skillSection, eduSection, workSection, projectSection, certificateSection, extraSection;
    LinearLayout mainLayout;
    Boolean visible = true;
    ScrollView scrollView;
    ImageButton eduAdd, eduDel;
    ImageButton workAdd, workDel;
    ImageButton skillAdd, skillDel;
    ImageButton projectAdd, projectDel;
    ImageButton certiAdd, certiDel;
    ImageButton extraAdd, extraDel;
    ImageView profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        profile = findViewById(R.id.profile);

        scrollView = findViewById(R.id.scrollView);
        skillSection = findViewById(R.id.skill);
        eduSection = findViewById(R.id.education);
        workSection = findViewById(R.id.work);
        skillSection = findViewById(R.id.skill);
        projectSection = findViewById(R.id.project);
        certificateSection = findViewById(R.id.certificate);
        extraSection = findViewById(R.id.extra);

        eduAdd = findViewById(R.id.addEdu);
        eduDel = findViewById(R.id.delEdu);

        workAdd = findViewById(R.id.addWork);
        workDel = findViewById(R.id.delWork);

        skillAdd = findViewById(R.id.addSkill);
        skillDel = findViewById(R.id.delSkill);

        projectAdd = findViewById(R.id.projectAdd);
        projectDel = findViewById(R.id.projectDel);

        certiAdd = findViewById(R.id.certiAdd);
        certiDel = findViewById(R.id.certiDel);

        extraAdd = findViewById(R.id.extraAdd);
        extraDel = findViewById(R.id.extraDel);


        eduAdd.setOnClickListener(this);
        workAdd.setOnClickListener(this);
        skillAdd.setOnClickListener(this);
        projectAdd.setOnClickListener(this);
        certiAdd.setOnClickListener(this);
        extraAdd.setOnClickListener(this);
        eduDel.setOnClickListener(this);
        workDel.setOnClickListener(this);
        skillDel.setOnClickListener(this);
        projectDel.setOnClickListener(this);
        certiDel.setOnClickListener(this);
        extraDel.setOnClickListener(this);


        mainLayout = findViewById(R.id.mainLayout);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 100);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            Uri targetUri = data.getData();
            Bitmap bitmap;
            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                profile.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    void hideViews(int input) {
        extraAdd.setVisibility(input);
        extraDel.setVisibility(input);
        workDel.setVisibility(input);
        workAdd.setVisibility(input);
        skillDel.setVisibility(input);
        skillAdd.setVisibility(input);
        projectAdd.setVisibility(input);
        projectDel.setVisibility(input);
        certiAdd.setVisibility(input);
        certiDel.setVisibility(input);
        eduAdd.setVisibility(input);
        eduDel.setVisibility(input);
    }

    void addExtraInput(LinearLayout parent, int res) {
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(res, parent, false);
        parent.addView(view);
    }

    void removeSection(LinearLayout section) {
        Snackbar.make(mainLayout, "Delete Section", Snackbar.LENGTH_LONG).setAction("Ok", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ViewGroup) section.getParent()).removeView(section);
                Toast.makeText(EditActivity.this, "Section Deleted", Toast.LENGTH_SHORT).show();
            }
        }).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.save:
                if (visible) {
                    hideViews(View.INVISIBLE);
                    visible = false;
                } else {
                    hideViews(View.VISIBLE);
                    visible = true;
                }
                takeScreenShot();
                finish();
                return true;
            default:
                return false;
        }
    }

    private void takeScreenShot() {

        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED) {
            createFolder();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    100);
        }

    }

    private void createFolder() {

        Utils utils = new Utils();
        String mainPath = utils.createFolder("", "ResumeBuilder");
        String imgPath = utils.createFolder("/ResumeBuilder", "Images");
        String pdfPath = utils.createFolder("/ResumeBuilder", "Pdf");

        int totalHeight = scrollView.getChildAt(0).getHeight();
        int totalWidth = scrollView.getChildAt(0).getWidth();
        Bitmap bitmap = utils.loadBitmapFromView(scrollView, totalWidth, totalHeight);
        if (utils.saveBitmap(bitmap, imgPath)) {
            Toast.makeText(this, "Saved image and pdf in ResumeBuilder ", Toast.LENGTH_LONG).show();
        }
        try {
            utils.savePdf(pdfPath + "/Resume_" + System.currentTimeMillis() + ".pdf", imgPath + "/resume.jpg");
        } catch (IOException | DocumentException e) {
            Toast.makeText(this,
                    "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.addEdu:
                addExtraInput(eduSection, R.layout.two_line);
                break;
            case R.id.delEdu:
                removeSection(eduSection);
                break;

            case R.id.addWork:
                addExtraInput(workSection, R.layout.two_line);
                break;
            case R.id.delWork:
                removeSection(workSection);
                break;
            case R.id.addSkill:
                addExtraInput(skillSection, R.layout.one_line);
                break;
            case R.id.delSkill:
                removeSection(skillSection);
                break;
            case R.id.projectAdd:
                addExtraInput(projectSection, R.layout.one_line);
                break;
            case R.id.projectDel:
                removeSection(projectSection);
                break;
            case R.id.certiAdd:
                addExtraInput(certificateSection, R.layout.one_line);
                break;
            case R.id.certiDel:
                removeSection(certificateSection);
                break;
            case R.id.extraAdd:
                addExtraInput(extraSection, R.layout.one_line);
                break;
            case R.id.extraDel:
                removeSection(extraSection);
                break;
        }
    }
}