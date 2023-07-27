package library;

import library.v1_1.domain.User;
import library.v1_1.util.DynamicArray;
import library.v1_1.repository.UserRepository;
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

    UserRepository ProxyRepo = (UserRepository) Proxy.newProxyInstance(MockitoTest.class.getClassLoader(), new Class[]{UserRepository.class}, new InvocationHandler() {
      @Override
      public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Invoked: " + method + " with args: " + Arrays.toString(args));
        if ("findAll".equals(method.getName())) {
          return new DynamicArray();
        } else if ("findById".equals(method.getName())) {
          return User.builder().id((Long) args[0]).build();
        }
        throw new UnsupportedOperationException();
      }
    });

    ProxyRepo.findAll();
    ProxyRepo.findById(1743L);
//    ProxyRepo.delete(null);

  }
}
