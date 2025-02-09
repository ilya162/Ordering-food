require: functions/funcs.js

bind("onAnyError", function($context) {
    var answerProblem = [
        "Простите, сервис временно не работает."
    ];
    $reactions.answer(answerProblem);
});

theme: OnlineOrders

    state: Start
        q!: $*start
        if: $request.age < 18;
            go!: /Minor
        else:
            go!: ./CheckChannel
            
        state: Minor
            a: Простите, сервис доступен только для совершеннолетних.
            script:
                $jsapi.stopSession;
            
        state: CheckChannel
            if: $request.channelType === "whatsapp"
                go!: /WhatsappOrder
            elseif: $request.channelType === "chatwidget"
                go!: /СhatwidgetOrder
            else:
                go!: /TelegramOrder
                
        state: WhatsappOrder
            TransferToOperator:
                messageBeforeTransfer = Перевожу на оператора.
            script:
                $jsapi.stopSession;
                
        state: СhatwidgetOrder
            a: Добро пожаловать, что вас интересует?
            timeout: /Goodbye || interval = "5 minutes"
            q!: *
            go!: /MainScenario
            
         state: TelegramOrder
            a: Добро пожаловать, что вас интересует?
            timeout: /Goodbye || interval = "5 minutes"
            q!: *
            go!: /MainScenario
            
        state: Wellcome
            a: Добро пожаловать, что вас интересует?
            q!: *
            go!: /MainScenario
            
        state: WhatsappOrder
            TransferToOperator:
                messageBeforeTransfer = Перевожу на оператора.
            script:
                $jsapi.stopSession;
            
        state: MainScenario
            a: 
            
            
