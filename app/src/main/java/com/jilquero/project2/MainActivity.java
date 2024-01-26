package com.jilquero.project2;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PhoneAdapter.OnItemClickListener {
    private PhoneModel mPhoneModel;
    private PhoneAdapter mAdapter;
    private FloatingActionButton FAButton;
    private ActivityResultLauncher<Intent> mARLauncher;
    public static final String PHONEUPDATE_RESULT = "phoneupdate_result";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPhoneModel = new ViewModelProvider(this).get(PhoneModel.class);
        FAButton = findViewById(R.id.AddButton);
        FAButton.setOnClickListener(view -> {
            startSecondActivity(null);
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int adapterPos = viewHolder.getAdapterPosition();
                mPhoneModel.delete(mPhoneModel.getAllPhones().getValue().get(adapterPos).getId());
            }
        });

        RecyclerView recyclerView = findViewById(R.id.Rview);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        mAdapter = new PhoneAdapter(this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mPhoneModel.getAllPhones().observe(this, items -> {
            mAdapter.setListPhones(items);
        });

        mARLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == InsertActivity.SAVE) {
                            Intent Rintent = result.getData();

                            Phone item = (Phone) Rintent.getSerializableExtra(InsertActivity.PHONE_RESULT);
                            mPhoneModel.insert(item);
                        }
                        if (result.getResultCode() == InsertActivity.UPDATE) {
                            Intent Rintent = result.getData();
                            Phone item = (Phone) Rintent.getSerializableExtra(InsertActivity.PHONE_RESULT);
                            mPhoneModel.update(item);
                        }
                    }
                }
        );
    }
    private void startSecondActivity(Phone phone) {
        Intent intent = new Intent(this, InsertActivity.class);
        intent.putExtra(PHONEUPDATE_RESULT, (Serializable) phone);
        mARLauncher.launch(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem mitem) {
        int id = mitem.getItemId();
        if (id == R.id.menu_delete_all) {
            mPhoneModel.deleteAll();
        }
        return super.onOptionsItemSelected(mitem);
    }
    @Override
    public void onItemClickListener(Phone item) {
        startSecondActivity(item);
    }
}