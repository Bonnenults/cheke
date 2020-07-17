package com.autozi.cheke.service.basic;


import com.autozi.cheke.basic.dao.RunningNumberDao;
import com.autozi.cheke.basic.entity.RunningNumber;
import com.autozi.common.utils.util2.DateTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class RunningNumberService {

    private static Logger logger = LoggerFactory.getLogger(RunningNumberService.class);
    private static long count = 1000;

    @Autowired
    private RunningNumberDao runningNumberDao;

    private Map<String, Queue<Long>> hashMap = new ConcurrentHashMap<String, Queue<Long>>();

    /**
     * <PRE>
     * <p/>
     * 中文描述：
     * <p/>
     * </PRE>
     *
     * @param encodable
     * @return
     * @作者 Lihf
     * @日期 2013-4-10
     */
    public String createOrderCode(RunningNumberEncodable encodable) {
        long serialNum = createNextNumber(encodable.getPrefix());
        return encodable.encode(serialNum);
    }

    /**
     * <PRE>
     * <p/>
     * 中文描述：
     * <p/>
     * </PRE>
     *
     * @param type
     * @return
     * @作者 Lihf
     * @日期 2013-4-10
     */
    public long createNextNumber(String type) {
        Queue<Long> queue = getQueue(type);
        Long value = null;
        for (int i = 0; i < 1; i++) {
            value = queue.poll();
            if (value == null) {
                boolean check = false;
                while (!check) {
                    check = initQueue(queue, type);
                }
                value = queue.poll();
            }
        }
        return value;
    }

    public long createNextNumber(String type, Long partyId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("type", type);
        map.put("partyId", partyId);
        List<RunningNumber> runningNumberList = runningNumberDao.findListForMap(map);
        long indexNumber = Long.valueOf(DateTools.getYYYYMM()) * 1000000;
        RunningNumber runningNumber;
        if (runningNumberList.size() > 0) {
            runningNumber = runningNumberList.get(0);
            Long oldNumber = Long.valueOf(runningNumber.getRunningNumber());
            if (Long.valueOf(DateTools.getYYYYMM()) * 10000 < oldNumber) {
                indexNumber =oldNumber;
            }
        } else {
            runningNumber = new RunningNumber();
            runningNumber.setType(type);
            runningNumber.setPartyId(partyId);
        }
        runningNumber.setRunningNumber(String.valueOf(indexNumber + 1));
        runningNumber.setRunningTime(DateTools.getCurrentDate());
        try {
            saveRunningNumber(runningNumber);
        } catch (Exception e) {
            e.printStackTrace();
        }
        long value = Long.valueOf(runningNumber.getRunningNumber());


        return value;
    }

    private boolean initQueue(Queue<Long> queue, String type) {
        logger.debug("the queue is init");

        RunningNumber runningNumber = runningNumberDao.findByType(type);
        long indexNumber = 0;
        if (runningNumber == null) {
            runningNumber = new RunningNumber();
            runningNumber.setType(type);
            runningNumber.setPartyId(0L);
        } else {
            indexNumber = Long.valueOf(runningNumber.getRunningNumber());
        }

        runningNumber.setRunningNumber(String.valueOf(indexNumber + count + 1));
        runningNumber.setRunningTime(DateTools.getCurrentDate());

        int check;
        try {
            check = saveRunningNumber(runningNumber);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        long i = indexNumber;
        while (i < indexNumber + count) {
            queue.add(i);
            i++;
        }
        return check > 0;
    }


    private Queue<Long> getQueue(String type) {
        Queue<Long> queue = hashMap.get(type);
        if (queue == null) {
            queue = new ConcurrentLinkedQueue<Long>();
            hashMap.put(type, queue);
        }
        return queue;
    }


    private int saveRunningNumber(RunningNumber runningNumber) throws Exception {
        if (runningNumber.getId() == null) {
            return runningNumberDao.insert(runningNumber);
        } else {
            return runningNumberDao.update(runningNumber);
        }
    }



}
