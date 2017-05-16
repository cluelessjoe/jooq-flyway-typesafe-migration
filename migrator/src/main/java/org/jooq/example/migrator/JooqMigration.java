package org.jooq.example.migrator;

import org.flywaydb.core.api.migration.jdbc.JdbcMigration;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultExecuteListenerProvider;
import org.jooq.tools.jdbc.JDBCUtils;

import java.sql.Connection;

public abstract class JooqMigration implements JdbcMigration {

    public static boolean ddlInstructionExecuted;
    public static boolean didExecute;

    @Override
    public final void migrate(Connection connection) throws Exception {
        didExecute = true;
        SQLDialect dialect = JDBCUtils.dialect(connection);
        if (SQLDialect.DEFAULT.equals(dialect))
            throw new IllegalStateException("Dialect couldn't be deducted from connection " + connection);

        Configuration configuration = new DefaultConfiguration().set(connection).set(dialect);
        DdlExecuteListener listener = new DdlExecuteListener();
        configuration.set(new DefaultExecuteListenerProvider(listener));
        DSLContext create = DSL.using(configuration);

        migrate(connection, create);
        ddlInstructionExecuted = listener.ddlInstructionExecuted();
    }

    public abstract void migrate(Connection connection, DSLContext create) throws Exception;

    private class DdlExecuteListener implements ExecuteListener {
        private boolean ddlInstructionExecuted = false;

        @Override
        public void start(ExecuteContext ctx) {
            detectDdlInstructionExecuted(ctx);
        }

        private void detectDdlInstructionExecuted(ExecuteContext ctx) {
            if (!ddlInstructionExecuted && ctx.type() == ExecuteType.DDL){
                ddlInstructionExecuted = true;
            }
        }

        @Override
        public void renderStart(ExecuteContext ctx) {
            detectDdlInstructionExecuted(ctx);
        }

        @Override
        public void renderEnd(ExecuteContext ctx) {
            detectDdlInstructionExecuted(ctx);
        }

        @Override
        public void prepareStart(ExecuteContext ctx) {
            detectDdlInstructionExecuted(ctx);
        }

        @Override
        public void prepareEnd(ExecuteContext ctx) {
            detectDdlInstructionExecuted(ctx);
        }

        @Override
        public void bindStart(ExecuteContext ctx) {
            detectDdlInstructionExecuted(ctx);
        }

        @Override
        public void bindEnd(ExecuteContext ctx) {
            detectDdlInstructionExecuted(ctx);
        }

        @Override
        public void executeStart(ExecuteContext ctx) {
            detectDdlInstructionExecuted(ctx);
        }

        @Override
        public void executeEnd(ExecuteContext ctx) {
            detectDdlInstructionExecuted(ctx);
        }

        @Override
        public void outStart(ExecuteContext ctx) {
            detectDdlInstructionExecuted(ctx);
        }

        @Override
        public void outEnd(ExecuteContext ctx) {
            detectDdlInstructionExecuted(ctx);
        }

        @Override
        public void fetchStart(ExecuteContext ctx) {
            detectDdlInstructionExecuted(ctx);
        }

        @Override
        public void resultStart(ExecuteContext ctx) {
            detectDdlInstructionExecuted(ctx);
        }

        @Override
        public void recordStart(ExecuteContext ctx) {
            detectDdlInstructionExecuted(ctx);
        }

        @Override
        public void recordEnd(ExecuteContext ctx) {
            detectDdlInstructionExecuted(ctx);
        }

        @Override
        public void resultEnd(ExecuteContext ctx) {
            detectDdlInstructionExecuted(ctx);
        }

        @Override
        public void fetchEnd(ExecuteContext ctx) {
            detectDdlInstructionExecuted(ctx);
        }

        @Override
        public void end(ExecuteContext ctx) {
            detectDdlInstructionExecuted(ctx);
        }

        @Override
        public void exception(ExecuteContext ctx) {
            detectDdlInstructionExecuted(ctx);
        }

        @Override
        public void warning(ExecuteContext ctx) {
            detectDdlInstructionExecuted(ctx);
        }

        public boolean ddlInstructionExecuted() {
            return ddlInstructionExecuted;
        }
    }
}
