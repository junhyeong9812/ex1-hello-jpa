package hellojpa;

import jakarta.persistence.Embeddable;

import java.time.LocalDateTime;

@Embeddable//값 타입을 정의하는 곳에서 값타입이라 알려줘야 한다.
public class Period {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
//기본 생성자 필수
    public Period() {
    }

    public Period(LocalDateTime startDate, LocalDateTime endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
}
