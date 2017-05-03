import org.jooq.util.DefaultGeneratorStrategy;
import org.jooq.util.Definition;

public class PrefixingGeneratorStrategy extends DefaultGeneratorStrategy {

    public PrefixingGeneratorStrategy() {
    }

    @Override
    public String getJavaClassName(final Definition definition, final Mode mode) {
        return "v3_" + super.getJavaClassName(definition, mode);
    }

    @Override
    public String getJavaIdentifier(Definition definition) {
        return "v3_" + super.getJavaIdentifier(definition);
    }

}
