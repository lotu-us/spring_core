package hello.core;

import hello.core.discount.*;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import org.springframework.context.annotation.Configuration;

@Configuration //스프링에서 관리할 수 있도록
public class AppConfig {
    //공연기획자 역할
    //애플리케이션 실제 동작에 필요한 구현객체를 생성한다
    //생성자를 통해서 주입한다(연결)

//    public MemberService memberService(){
//        return new MemberServiceImpl(new MemoryMemberRepository());
//    }
//    //MemberServiceImpl에 보면 아래와 같은 부분이 있다.
//    //private final MemberRepository memberRepository = new MemoryMemberRepository();
//    //이것은 "로미오역할"이 "이병헌"을 직접 섭외하는 것과 같은 내용이다
//    //생성자 주입을 통해 해결하자
//    //MemberServiceImpl 주석 참고하자
//
//    public OrderService orderService(){
//        return new OrderServiceImpl(new MemoryMemberRepository(), new FixDiscountPolicy());
//    }

    //위에까지는 MemoryMemberRepository() 중복이 발생하고,
    //역할에 따른 구현이 잘 안보인다
    //아래처럼 리팩터링하자
    //AppConfig를 사용함으로써 애플리케이션을 사용영역과 구성영역으로 나누게되었다
    //따라서 FixDiscountPolicy에서 RateDiscountPolicy로 변경할 때
    //클라이언트 코드(사용영역)은 변경하지 않아도 된다 = 변경 닫혀있다
    //구성영역(AppConfig)만 변경하면된다 => AppConfig만 바꾸면 되니 확장에 열려있다
    
    public MemberRepository memberRepository(){
        return new MemoryMemberRepository();
    }

    public DiscountPolicy discountPolicy(){
        return new RateDiscountPolicy();
    }

    public MemberService memberService(){
        return new MemberServiceImpl(memberRepository());
    }

    public OrderService orderService(){
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    //AppConfig를 사용함으로써 클라이언트코드는 주어진 행위을 실행만 할뿐이지
    //누가 실행할지는 모른다! 프로그램의 제어권이 AppConfig에 있는 것
    //이렇듯 프로그램의 제어 흐름을 클라이언트코드가 아닌 외부(AppConfig)에서
    //관리하는 것을 제어의 역전(inversion of Control)이라 한다
    
    //애플리케이션 실행시점에 외부(AppConfig)에서 실제 구현 객체를 생성하고 클라이언트에 전달해서
    //클라이언트와 서버의 실제 의존관계가 연결되는 것을 "의존관계 주입"이라 한다
    //객체 인스턴스를 생성하고, 그 참조값을 전달해서 연결된다
    //의존관계 주입을 사용하면 클라이언트 코드를 변경하지 않고,
    //클라이언트가 호출하는 대상의 타입 인스턴스를 변경할 수 있다
    //의존관계 주입을 사용하면 정적인 클래스 의존관계(import)를 변경하지 않고,
    //동적인 객체 인스턴스 의존관계를 쉽게 변경할 수 있다.


    //AppConfig처럼 객체를 생성하고 관리하면서 의존관계를 연결해주는 것을
    //IoC컨테이너(AppConfig로 인해 제어의 역전이 일어났으니까) 또는
    //DI컨테이너(AppConfig에서 의존관계를 주입하니까) 라고 한다.
    //요즘엔 의존관계 주입에 초점을 맞추어 DI컨테이너라고 한다
    //(IoC컨테이너는 너무 범용적으로 사용되는 느낌이라한다)

}
