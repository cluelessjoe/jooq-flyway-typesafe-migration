import org.jooq.util.AbstractDatabase;
import org.jooq.util.DefaultGeneratorStrategy;
import org.jooq.util.Definition;

public class PrefixingGeneratorStrategy extends DefaultGeneratorStrategy {

    @Override
    public String getJavaClassName(final Definition definition, final Mode mode) {
        return getVersion(definition) + "_" + super.getJavaClassName(definition, mode);
    }

    @Override
    public String getJavaIdentifier(Definition definition) {
        return getVersion(definition) + "_" + super.getJavaIdentifier(definition);
    }

    private String getVersion(Definition definition) {
        return ((AbstractDatabase) definition.getDatabase()).getProperties().getProperty(ModelSynchronizer.VERSION_PROPERTY_KEY);
    }

}
