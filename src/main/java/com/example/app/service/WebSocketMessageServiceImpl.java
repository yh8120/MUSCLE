package com.example.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.app.dao.TrainingLogDao;
import com.example.app.dao.UserDao;
import com.example.app.domain.MUser;
import com.example.app.domain.Notice;
import com.example.app.domain.TrainingLog;
import com.example.app.domain.TrainingSet;

@Service
@Transactional
@Async
public class WebSocketMessageServiceImpl implements WebSocketMessageService {

	@Autowired
	private SimpMessagingTemplate template;
	@Autowired
	private TrainingLogDao trainingLogDao;
	@Autowired
	private UserDao userDao;

	@Override
	public void sendTrainingLog(TrainingLog trainingLog) throws Exception {
		String body = trainingLog.getTraining().getName() + ":";
		List<TrainingSet> trainingSetList = trainingLog.getTrainingSetList();
		for (int i = 0; i < trainingSetList.size(); i++) {
			TrainingSet set = trainingSetList.get(i);
			body += set.getWeight() + "kg×" + set.getRep();
			if (i != trainingSetList.size() - 1) {
				body += ",";
			}
		}
		MUser user = userDao.selectById(trainingLog.getUser().getId());

		template.convertAndSend("/topic/notice",
				new Notice(trainingLog.getId(), user.getId(), body, user.getIconPath(), null));
	}

	@Override
	public void sendTrainingLogToUser(String userName) throws Exception {
		Thread.sleep(3000);
		List<TrainingLog> trainingLogList = trainingLogDao.findLogListNewer();
		for (TrainingLog trainingLog : trainingLogList) {
			String body = trainingLog.getTraining().getName() + ":";
			List<TrainingSet> trainingSetList = trainingLog.getTrainingSetList();
			for (int i = 0; i < trainingSetList.size(); i++) {
				TrainingSet set = trainingSetList.get(i);
				body += set.getWeight() + "kg×" + set.getRep();
				if (i != trainingSetList.size() - 1) {
					body += ",";
				}
			}
			template.convertAndSendToUser(userName, "/private",
					new Notice(trainingLog.getId(), trainingLog.getUser().getId(), body,
							trainingLog.getUser().getIconPath(), trainingLog.getContributorList()));
		}
	}

	@Override
	public void sendProteinToUser(Integer trainingLogId) throws Exception {
		TrainingLog trainingLog = trainingLogDao.findById(trainingLogId);
		MUser user = userDao.selectById(trainingLog.getUser().getId());
		String body = "あなたの" + trainingLog.getTraining().getName() + "に対してプロテインが送られました♪";
		String iconPath = "notice.svg";

		template.convertAndSendToUser(user.getEmail(), "/private", new Notice(null, null, body, iconPath, null));
	}

	@Override
	public void sendConnection(String userName) throws Exception {
		String iconPath = "notice.svg";
		template.convertAndSendToUser(userName, "/private", new Notice(null, null, "接続が完了しました。", iconPath, null));

	}
}
