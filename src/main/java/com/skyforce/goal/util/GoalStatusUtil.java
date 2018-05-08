package com.skyforce.goal.util;

import com.skyforce.goal.model.Goal;
import com.skyforce.goal.model.User;
import com.skyforce.goal.model.enums.GoalState;
import com.skyforce.goal.repository.UserRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

@Component
public class GoalStatusUtil {
    private UserRepository userRepository;

    private GoalUpdater goalUpdater;

    // One hour
    private long delay = 60 * 60 * 1000;

    @Autowired
    public GoalStatusUtil(UserRepository userRepository) {
        this.userRepository = userRepository;
        goalUpdater = new GoalUpdater(delay);
    }

    public void disableRegularUpdate() {
        if (goalUpdater != null) {
            goalUpdater.stop();
        }
    }

    public void enableRegulularUpdate() {
        // Start update at the beginning of the next hour.
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.HOUR, 1);
        Timer timer = new Timer();
        timer.schedule(goalUpdater, calendar.getTime());
    }

    public void update() {
        goalUpdater.update();
    }

    private class GoalUpdater extends TimerTask {

        @Getter
        @Setter
        private long delay;
        private boolean stop = false;

        public GoalUpdater(long delay) {
            this.delay = delay;
        }

        private void stop() {
            stop = true;
        }

        @Override
        public void run() {
            while (!stop) {
                update();
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    System.err.println("Goal update checker interrupted");
                }
            }
        }

        @EventListener(ApplicationReadyEvent.class)
        public void update() {
            Date currentDate = new Date();
            for (User user : userRepository.findAll()) {
                for (Goal goal : user.getGoalsByState(GoalState.INPROGRESS)) {
                    if (goal.getDateEnd().compareTo(currentDate) > 0) {
                        goal.setState(GoalState.FAILED);
                    }
                }
            }
        }
    }
}
