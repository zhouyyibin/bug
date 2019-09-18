package seed.controller.body;

import java.util.List;

public class ModelBody {
    String name;
    String leadingId;
    String leadingName;
    String leadingAccount;
    List<Sender> senders;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLeadingId() {
        return leadingId;
    }

    public void setLeadingId(String leadingId) {
        this.leadingId = leadingId;
    }

    public String getLeadingName() {
        return leadingName;
    }

    public void setLeadingName(String leadingName) {
        this.leadingName = leadingName;
    }

    public String getLeadingAccount() {
        return leadingAccount;
    }

    public void setLeadingAccount(String leadingAccount) {
        this.leadingAccount = leadingAccount;
    }

    public List<Sender> getSenders() {
        return senders;
    }

    public void setSenders(List<Sender> senders) {
        this.senders = senders;
    }

   public static class Sender{
        Long id;
        String name;
        String account;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }
    }
}
