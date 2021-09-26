package hello.core.member;

public class MemberServiceImpl implements MemberService {
//    private final MemberRepository memberRepository = new MemoryMemberRepository();
//    AppConfig파일에서 대체함
    private final MemberRepository memberRepository;
    public MemberServiceImpl(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    //코드를 살펴보면 그 어디에도 구현객체는 살펴볼수없고
    //오로지 MemberRepository 인터페이스에만 의존하고있다!! DIP 완성!
    //예전엔 인터페이스에서 어떤 객체를 쓸지도 고민했어야했는데
    //이제 더이상 인터페이스의 관심사가 아니게됨! 인터페이스는 "실행"만 하면된다
    //AppConfig에서는 객체를 생성하고 연결만 해주면 알아서 실행되니까~
    //AppConfig의 관심사도 "실행"이 아닌 "생성, 연결"에만 집중하면됨!

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
