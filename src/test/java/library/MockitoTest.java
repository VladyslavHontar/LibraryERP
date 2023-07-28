package library;

import library.v2_0.domain.User;
import library.v2_0.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class A {       // mocked class cannot be final
  public Integer[] method() {     // method class cannot be final
    System.out.println("Hello from A");
    return new Integer[]{1};
  }
}
class B {      // mocked class cannot be final
  public Integer method() {       // method class cannot be final
    System.out.println("Hello from B");
    return 1;
  }
}

class C {     // mocked class cannot be final
  public int method() {           // method class cannot be final
    System.out.println("Hello from C");
    return 1;
  }
}

public class MockitoTest {
  @Test
  void proxyObjects() {
    A mockA = Mockito.mock(A.class);
    Integer[] mockedAValue = mockA.method();
    assertThat(mockedAValue).isNull();

    B mockB = Mockito.mock(B.class);
    Integer mockedBValue = mockB.method();
    assertThat(mockedBValue).isZero();               //???? why not null ????

    C mockC = Mockito.mock(C.class);
    int mockedCValue = mockC.method();
    assertThat(mockedCValue).isZero();

    UserRepository repo = Mockito.mock(UserRepository.class);

    System.out.println(repo.getClass());
    System.out.println(repo.getClass().isAssignableFrom(UserRepository.class));     //???????
    System.out.println(repo instanceof UserRepository);

  }
}
