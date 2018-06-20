package com.ebitg.spring.tx.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ebitg.spring.tx.T1;
import com.ebitg.spring.tx.dao.MyDao;

@Service("myService")

public class MyServiceImpl implements MyService {
	@Autowired
	private MyDao myDao;

	@Override
	public void doSome() {
		System.out.println(">>>>>>>>׼����ѯ���еļ�¼");
		List<T1> t1list = myDao.findAll();
		System.out.println(Thread.currentThread().getName() + "����ѯ��" + t1list.size() + "����¼\n" + t1list);
		int count = 0;
		for (int i = 0; i < t1list.size(); i++) {
			T1 t1 = t1list.get(i);
			count += update(t1);
			System.out.println("++++++++++"+Thread.currentThread().getName() + "��"+i+"�θ������");
		}
		System.out.println("<<<<<<<<" + Thread.currentThread().getName() + "�����½��(�ɹ�/��ץȡ��)=(" + count + "/"
				+ t1list.size() + ")����¼\n" + t1list);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	private int update(T1 t1) {
		// T1 t1db = myDao.syncGet(t1.getId());
		T1 t1db = t1;
		if (null == t1db.getUpdateTime()) {
			t1db.setUpdateTime(new Date());
			t1db.addCount();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			myDao.update(t1db);
			System.out.println(Thread.currentThread().getName() + ":" + t1.getId() + "1.���³ɹ�");
			return 1;
		}
		System.out.println(Thread.currentThread().getName() + ":" + t1.getId() + "0.�Ѿ�����");
		return 0;
	}

}
