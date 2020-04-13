package id.web.noxymon.db.models;

public class TransactionCountModel {
    private Integer id;
    private Integer usage;
    private Integer version;

    public TransactionCountModel(Integer id, Integer usage, Integer version) {
        this.id = id;
        this.usage = usage;
        this.version = version;
    }

    public TransactionCountModel(TransactionCountModel other) {
        this.id = other.id;
        this.usage = other.usage;
        this.version = other.version;
    }

    public Integer getId() {
        return id;
    }

    public Integer getUsage() {
        return usage;
    }

    public Integer getVersion() {
        return version;
    }
}
