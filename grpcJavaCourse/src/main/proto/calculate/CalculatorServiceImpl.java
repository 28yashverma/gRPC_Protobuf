package calculate;

import com.proto.calculate.Calculate;
import com.proto.calculate.CalculateRequest;
import com.proto.calculate.CalculateResponse;
import com.proto.calculate.CalculatorServiceGrpc.*;
import io.grpc.stub.StreamObserver;

public class CalculatorServiceImpl extends CalculatorServiceImplBase {

    @Override
    public void calculate(CalculateRequest request, StreamObserver<CalculateResponse> responseObserver) {
        Calculate calculate = request.getCalculate();
        Integer firstNumber = calculate.getFirstNumber();
        Integer secondNumber = calculate.getSecondNumber();

        Integer sum = firstNumber + secondNumber;

        CalculateResponse response = CalculateResponse.newBuilder()
                .setSum(sum)
                .build();

        responseObserver.onNext(response);

        responseObserver.onCompleted();
    }
}
