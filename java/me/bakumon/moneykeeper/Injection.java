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

package me.bakumon.moneykeeper;

import android.content.Context;

import me.bakumon.moneykeeper.database.AppDatabase;
import me.bakumon.moneykeeper.datasource.AppDataSource;
import me.bakumon.moneykeeper.datasource.LocalAppDataSource;
import me.bakumon.moneykeeper.viewmodel.ViewModelFactory;

public class Injection {
    private Context context;

    public Injection(Context context) {
        this.context = context;
    }

    public static AppDataSource provideUserDataSource(Context context) {
        AppDatabase database = AppDatabase.getInstance(context); // 传递 Context 参数
        return new LocalAppDataSource(database);
    }

    public static ViewModelFactory provideViewModelFactory(Context context) {
        AppDataSource dataSource = provideUserDataSource(context); // 传递 Context 参数
        return new ViewModelFactory(dataSource);
    }
}
