package cn.az.sec.redis;

public abstract class BasePrefix implements KeyPrefix {

    private final String prefix;

    public BasePrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String getPrefix() {
        String className = getClass().getSimpleName();
        return className + ":" + prefix;
    }

}
