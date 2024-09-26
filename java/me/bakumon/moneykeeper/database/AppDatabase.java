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

package me.bakumon.moneykeeper.database;



import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import me.bakumon.moneykeeper.App;
import me.bakumon.moneykeeper.database.converters.Converters;
import me.bakumon.moneykeeper.database.dao.RecordDao;
import me.bakumon.moneykeeper.database.dao.RecordTypeDao;
import me.bakumon.moneykeeper.database.entity.Record;
import me.bakumon.moneykeeper.database.entity.RecordType;



@Database(entities = {Record.class, RecordType.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    public static final String DB_NAME = "MoneyKeeper.db";

    private static volatile AppDatabase INSTANCE;

    private Context mContext;

    public AppDatabase() {
        super();
        // 初始化代码，如果需要的话
    }

    public AppDatabase(Context context) {
        super();
        this.mContext = context.getApplicationContext(); // 使用应用上下文避免内存泄漏
    }

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, DB_NAME)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public Context getContext() {
        return mContext; // 提供一个公共方法来获取 Context
    }

    public abstract RecordTypeDao recordTypeDao();

    public abstract RecordDao recordDao();
}