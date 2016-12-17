package applyingjava.java.streams.mapper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import applyingjava.java.streams.util.LOG;

public class Mapper {
    private static final DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");

    public static Order mapOrders(String line) {
        String[] split = line.split(",");
        Order order = null;
        try {
            order = new Order();
            order.setId(Long.valueOf(split[0]));
            order.setCreateTime(formatter.parse(split[1]));
            order.setCustId(Long.valueOf(split[2]));
            order.setStatus(Status.valueOf(split[3]));
        } catch (ParseException e) {
            LOG.print.error(e.getMessage());
        }
        return order;
    }

    public static class Order {
        private Long id;
        private Date createTime;
        private Long custId;
        private Status status;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Date getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Date createTime) {
            this.createTime = createTime;
        }

        public Long getCustId() {
            return custId;
        }

        public void setCustId(Long orderNbr) {
            this.custId = orderNbr;
        }

        public Status getStatus() {
            return status;
        }

        public void setStatus(Status status) {
            this.status = status;
        }

        @Override
        public String toString() {
            return "Order [id=" + id + ", createTime=" + createTime + ", custId=" + custId + ", status=" + status + "]";
        }

    }

    public enum Status {
        CLOSED, PENDING_PAYMENT, COMPLETE, PROCESSING, PAYMENT_REVIEW, PENDING, ON_HOLD, CANCELED, SUSPECTED_FRAUD
    }
}
