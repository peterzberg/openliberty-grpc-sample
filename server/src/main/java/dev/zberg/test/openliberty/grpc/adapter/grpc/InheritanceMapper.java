package dev.zberg.test.openliberty.grpc.adapter.grpc;


import dev.zberg.test.openliberty.grpc.AInheritance;
import dev.zberg.test.openliberty.grpc.RequestWithInheritance;
import dev.zberg.test.openliberty.grpc.adapter.api.A;
import dev.zberg.test.openliberty.grpc.adapter.api.B;
import dev.zberg.test.openliberty.grpc.adapter.api.C;
import dev.zberg.test.openliberty.grpc.adapter.api.CompositeResponse;

class InheritanceMapper {
    public A map(RequestWithInheritance request) {
        final AInheritance a = request.getA();
        return switch (a.getInheritanceCase()) {
            case B -> {
                final dev.zberg.test.openliberty.grpc.B protoB = a.getB();
                final B b = new B();
                b.setbProperty(protoB.getBProperty());
                final dev.zberg.test.openliberty.grpc.A protoA = protoB.getSuperType();
                b.setaProperty(protoA.getAProperty());
                yield b;
            }
            case C -> {
                dev.zberg.test.openliberty.grpc.C protoC = a.getC();
                final C c = new C();
                c.setcProperty(protoC.getCProperty());
                final dev.zberg.test.openliberty.grpc.B protoB = protoC.getSuperType();
                c.setbProperty(protoB.getBProperty());
                dev.zberg.test.openliberty.grpc.A protoA = protoB.getSuperType();
                c.setaProperty(protoA.getAProperty());
                yield c;
            }
            case INHERITANCE_NOT_SET -> throw new IllegalStateException("Unknown composite");
        };
    }

    public dev.zberg.test.openliberty.grpc.ResponseWithInheritance map(CompositeResponse compositeResponse) {
        final AInheritance.Builder aBuilder = AInheritance.newBuilder();
        final A responseA = compositeResponse.getResponseA();
        if (responseA.getClass() == B.class) {
            final B b = (B) responseA;
            // parent of B
            final dev.zberg.test.openliberty.grpc.A.Builder parentOfBBuilder = dev.zberg.test.openliberty.grpc.A.newBuilder();
            parentOfBBuilder.setAProperty(b.getaProperty());
            dev.zberg.test.openliberty.grpc.A parentOfB = parentOfBBuilder.build();
            // B
            dev.zberg.test.openliberty.grpc.B.Builder bBuilder = dev.zberg.test.openliberty.grpc.B.newBuilder();
            bBuilder.setSuperType(parentOfB); // set parent
            bBuilder.setBProperty(b.getbProperty()); // set properties
            dev.zberg.test.openliberty.grpc.B protoB = bBuilder.build();
            aBuilder.setB(protoB);
        } else if (responseA.getClass() == C.class) {
            final C c = (C) responseA;
            // parent of B
            final dev.zberg.test.openliberty.grpc.A.Builder parentOfBBuilder = dev.zberg.test.openliberty.grpc.A.newBuilder();
            parentOfBBuilder.setAProperty(c.getaProperty());
            dev.zberg.test.openliberty.grpc.A parentOfB = parentOfBBuilder.build();
            // parent of C
            dev.zberg.test.openliberty.grpc.B.Builder bBuilder = dev.zberg.test.openliberty.grpc.B.newBuilder();
            bBuilder.setSuperType(parentOfB); // set parent
            bBuilder.setBProperty(c.getbProperty());
            dev.zberg.test.openliberty.grpc.B parentOfC = bBuilder.build();
            // C
            dev.zberg.test.openliberty.grpc.C.Builder cBuilder = dev.zberg.test.openliberty.grpc.C.newBuilder();
            cBuilder.setSuperType(parentOfC); // set parent
            cBuilder.setCProperty(c.getcProperty());
            dev.zberg.test.openliberty.grpc.C protoC = cBuilder.build();
            aBuilder.setC(protoC);
        }
        return dev.zberg.test.openliberty.grpc.ResponseWithInheritance.newBuilder().setType(compositeResponse.getType()).setA(aBuilder.build()).build();
    }
}
