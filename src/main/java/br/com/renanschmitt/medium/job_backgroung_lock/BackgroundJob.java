package br.com.renanschmitt.medium.job_backgroung_lock;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@RequiredArgsConstructor
public class BackgroundJob {

  private final RedisLockRegistry redisLockRegistry;
  private final RedissonClient redissonClient;

  //  @Scheduled(fixedDelay = 2000)
  //  public void execute() {
  //    Lock lock = redisLockRegistry.obtain("background-job-key");
  //
  //    if (lock.tryLock()) {
  //      try {
  //        log.info("Executing background job");
  //        try {
  //          Thread.sleep(1000);
  //        } catch (InterruptedException e) {
  //          log.error("Error executing background job", e);
  //        }
  //        log.info("Background job executed");
  //      } finally {
  //        lock.unlock();
  //      }
  //    } else {
  //      log.warn("Could not acquire lock");
  //    }
  //  }

  @Scheduled(fixedDelay = 2000)
  public void execute() {
    RLock lock = redissonClient.getLock("background-job-key");

    try {
      if (lock.tryLock(50, 1500, TimeUnit.MILLISECONDS)) {
        try {
          log.info("Executing background job");
          try {
            Thread.sleep(1000);
          } catch (InterruptedException e) {
            log.error("Error executing background job", e);
          }
          log.info("Background job executed");
        } finally {
          lock.unlock();
        }
      } else {
        log.warn("Could not acquire lock");
      }
    } catch (InterruptedException e) {
      log.error("Interrupted: ", e);
    }
  }
}
