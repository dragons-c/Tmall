package ecjtu.mall.service.impl;

import ecjtu.mall.mapper.ReviewMapper;
import ecjtu.mall.pojo.Review;
import ecjtu.mall.pojo.ReviewExample;
import ecjtu.mall.pojo.User;
import ecjtu.mall.service.ReviewService;
import ecjtu.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
@Service
@RequestMapping("")
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    ReviewMapper reviewMapper;
    @Autowired
    UserService userService;
    @Override
    public void add(Review review) {
        reviewMapper.insert(review);
    }

    @Override
    public void delete(int id) {
        reviewMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Review review) {
        reviewMapper.updateByPrimaryKeySelective(review);
    }

    @Override
    public Review get(int id) {
        return reviewMapper.selectByPrimaryKey(id);
    }

    @Override
    public List list(int pid) {
        ReviewExample example = new ReviewExample();
        example.createCriteria().andPidEqualTo(pid);
        example.setOrderByClause("id desc");
        List<Review> reviews = reviewMapper.selectByExample(example);
        setUser(reviews);
        return reviews;
    }

    @Override
    public int getCount(int pid) {
        return list(pid).size();
    }
    public void setUser(List<Review> reviews){
        for (Review review:reviews
             ) {
            setUser(review);
        }
    }
    public void setUser(Review review){
       int uid = review.getUid();
        User user = userService.get(uid);
        review.setUser(user);

    }
}
