/*
 * Copyright 2018 Bakumon. https://github.com/Bakumon
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package me.bakumon.moneykeeper.ui.setting;


import androidx.lifecycle.ViewModel;

import java.io.File;
import java.util.List;

import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import me.bakumon.moneykeeper.utill.BackupUtil;

/**
 * 设置 ViewModel
 *
 * @author Bakumon https://bakumon.me
 */
import android.content.Context;

public class SettingViewModel extends ViewModel {

    public Flowable<List<BackupBean>> getBackupFiles(Context context) {
        return Flowable.create(e -> {
            try {
                e.onNext(BackupUtil.getBackupFiles(context));
                e.onComplete();
            } catch (Exception ex) {
                e.onError(ex);
            }
        }, BackpressureStrategy.BUFFER);
    }

    public Completable backupDB(Context context) {
        return Completable.create(e -> {
            try {
                boolean result = BackupUtil.userBackup(context);
                if (result) {
                    e.onComplete();
                } else {
                    e.onError(new Exception("Backup failed"));
                }
            } catch (Exception ex) {
                e.onError(ex);
            }
        });
    }

    public Completable restoreDB(Context context, String restoreFile) {
        return Completable.create(e -> {
            try {
                boolean result = BackupUtil.restoreDB(context, new File(restoreFile));
                if (result) {
                    e.onComplete();
                } else {
                    e.onError(new Exception("Restore failed"));
                }
            } catch (Exception ex) {
                e.onError(ex);
            }
        });
    }
}
