package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) //읽기만 할 경우에는 readOnly 사용
@RequiredArgsConstructor
public class MemberService {

    //@Autowired //스프링 빈에 등록되어 있던 repository를 injection해줌
    private final MemberRepository memberRepository;

    /*
    //@Autowired //생성자가 하나만 있는 경우에는 스프링이 자동으로 @Autowired injection을 해줌
    public MemberService(MemberRepository memberRepository){ //생성자 injection
        this.memberRepository = memberRepository;
    }

    @RequiredArgsConstructor를 사용하면 자동으로 만들어준다.
     */

    /* 회원 가입
    @Transactional안에 @Transactional이 또 있을 경우 얘가 더 우선권을 가진다.
    default는 (readOnly = false)
    */
    @Transactional
    public Long join(Member member){

        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member){
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원 전체 조회

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }

}
