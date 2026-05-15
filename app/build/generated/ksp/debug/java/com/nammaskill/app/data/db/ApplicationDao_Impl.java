package com.nammaskill.app.data.db;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class ApplicationDao_Impl implements ApplicationDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ApplicationEntity> __insertionAdapterOfApplicationEntity;

  public ApplicationDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfApplicationEntity = new EntityInsertionAdapter<ApplicationEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `applications` (`id`,`courseId`,`courseTrade`,`centerName`,`createdAtEpochMs`) VALUES (?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ApplicationEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getCourseId());
        statement.bindString(3, entity.getCourseTrade());
        statement.bindString(4, entity.getCenterName());
        statement.bindLong(5, entity.getCreatedAtEpochMs());
      }
    };
  }

  @Override
  public Object upsert(final ApplicationEntity application,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfApplicationEntity.insert(application);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<ApplicationEntity>> observeAll() {
    final String _sql = "SELECT * FROM applications ORDER BY createdAtEpochMs DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"applications"}, new Callable<List<ApplicationEntity>>() {
      @Override
      @NonNull
      public List<ApplicationEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCourseId = CursorUtil.getColumnIndexOrThrow(_cursor, "courseId");
          final int _cursorIndexOfCourseTrade = CursorUtil.getColumnIndexOrThrow(_cursor, "courseTrade");
          final int _cursorIndexOfCenterName = CursorUtil.getColumnIndexOrThrow(_cursor, "centerName");
          final int _cursorIndexOfCreatedAtEpochMs = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAtEpochMs");
          final List<ApplicationEntity> _result = new ArrayList<ApplicationEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ApplicationEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpCourseId;
            _tmpCourseId = _cursor.getString(_cursorIndexOfCourseId);
            final String _tmpCourseTrade;
            _tmpCourseTrade = _cursor.getString(_cursorIndexOfCourseTrade);
            final String _tmpCenterName;
            _tmpCenterName = _cursor.getString(_cursorIndexOfCenterName);
            final long _tmpCreatedAtEpochMs;
            _tmpCreatedAtEpochMs = _cursor.getLong(_cursorIndexOfCreatedAtEpochMs);
            _item = new ApplicationEntity(_tmpId,_tmpCourseId,_tmpCourseTrade,_tmpCenterName,_tmpCreatedAtEpochMs);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
