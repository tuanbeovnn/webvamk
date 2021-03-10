package com.vamkthesis.web.api.socket.handler;//package com.livestreamapp.socket.handler;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.livestreamapp.config.JwtTokenUtil;
//import com.livestreamapp.dto.MyUserDTO;
//import com.livestreamapp.dto.OnsaleDTO;
//import com.livestreamapp.enums.LoginModeEnum;
//import com.livestreamapp.exception.ClientException;
//import com.livestreamapp.redis.input.UserInput;
//import com.livestreamapp.redis.model.*;
//import com.livestreamapp.redis.repository.ChannelLiveRedisRepository;
//import com.livestreamapp.redis.repository.ChannelViewRedisRepository;
//import com.livestreamapp.redis.repository.OnsaleRedisRepository;
//import com.livestreamapp.redis.repository.UserRedisRepository;
//import com.livestreamapp.service.impl.ChannelLiveService;
//import com.livestreamapp.service.impl.OnsaleService;
//import com.livestreamapp.socket.config.DataUtils;
//import com.livestreamapp.util.Snapshot;
//import io.jsonwebtoken.Claims;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.CloseStatus;
//import org.springframework.web.socket.TextMessage;
//import org.springframework.web.socket.WebSocketSession;
//import org.springframework.web.socket.handler.TextWebSocketHandler;
//
//import java.io.IOException;
//import java.util.*;
//
////import com.livestreamapp.entity.Channel;
//
//
//class TextMessageBuilder {
//    Map res;
//
//    private TextMessageBuilder() {
//    }
//
//    public static TextMessageBuilder getBuilder() {
//        TextMessageBuilder textMessageBuilder = new TextMessageBuilder();
//        textMessageBuilder.res = new HashMap();
//        return textMessageBuilder;
//    }
//
//    public TextMessageBuilder set(String key, Object object) {
//        res.put(key, object);
//        return this;
//    }
//
//    public TextMessage build() throws JsonProcessingException {
//        return new TextMessage(new ObjectMapper().writeValueAsString(res));
//    }
//}
//
//@Component
//public class SocketHandler extends TextWebSocketHandler {
//
//    protected final Log logger = LogFactory.getLog(SocketHandler.class);
//
//    @Autowired
//    DataUtils dataUtils;
//
//    @Autowired
//    private JwtTokenUtil jwtTokenUtil;
//
//    @Autowired
//    private ChannelLiveService channelLiveService;
//
//
//    @Autowired
//    private OnsaleService onsaleService;
//
//    @Autowired
//    private ChannelLiveRedisRepository channelLiveRedisRepository;
//
//    @Autowired
//    private ChannelViewRedisRepository channelViewRedisRepository;
//
//    @Autowired
//    private OnsaleRedisRepository onsaleRedisRepository;
//
//    @Autowired
//    private UserRedisRepository userRedisRepository;
//
//    @Autowired
//    ObjectMapper objectMapper;
//
//    @Autowired
//    private Snapshot snapshot;
//
//    @Override
//    public void handleTextMessage(WebSocketSession session, TextMessage message)
//            throws InterruptedException, IOException {
//        MyUserDTO myUserDTO = null;
//        User sessionUser = userRedisRepository.findBySessionId(session.getId())
//                .orElse(null);
//        if (sessionUser != null)
//            myUserDTO = sessionUser.getUser();
//        try {
//            //session.sendMessage(message);
//            Payload payload = objectMapper.readValue(message.getPayload(), Payload.class);
//            System.out.println(message.getPayload());
//            String topic = payload.getTopic();
//            String payloadData = payload.getData().replace("\\", "");
//            switch (topic) {
////                case "PING":
////                    TextMessage textMessage = TextMessageBuilder.getBuilder()
////                            .set("topic", "PONG")
////                            .build();
////                    session.sendMessage(textMessage);
////                    break;
//
////                case "CONNECT_SOCKET":
////                    UserInput userInput = objectMapper.readValue(payloadData, UserInput.class);
////                    logger.info(String.format("Request connect soccket from %s", session.getId()));
////                    Claims claims = jwtTokenUtil.getAllClaimsFromToken(userInput.getToken());
////                    myUserDTO = new ModelMapper().map(claims, MyUserDTO.class);
////                    Authentication auth = new UsernamePasswordAuthenticationToken(myUserDTO, null, null);
////                    SecurityContextHolder.getContext().setAuthentication(auth);
////                    User user = userRedisRepository.findByUserId(myUserDTO.getId()).orElse(new User());
////                    user.setId(myUserDTO.getId());
////                    user.setUser(myUserDTO);
////                    user.setSessionId(session.getId());
////                    dataUtils.getSessions().put(user.getId(), session);
////                    message = TextMessageBuilder.getBuilder()
////                            .set("topic", topic)
////                            .set("message", String.format("Connect successfully. Welcome %s", myUserDTO.getUsername()))
////                            .build();
////                    session.sendMessage(message);
////                    userRedisRepository.save(user);
////                    logger.info(String.format("User id %d connect soccket success. session_id %s", myUserDTO.getId(), session.getId()));
////                    break;
//
//                case "LIVE_START":
////                    logger.info(String.format("Seller id %d request start live", myUserDTO.getId()));
////                    if (myUserDTO.getMode() != LoginModeEnum.SELLER.getMode()) return;
////                    ChannelLive channel = objectMapper.readValue(payloadData, ChannelLive.class);
////                    ChannelLive entity = channelLiveRedisRepository.findById(channel.getId()).orElse(new ChannelLive());
////                    channelLiveService.startLive(channel.getId());
////                    entity.setId(channel.getId());
////                    entity.setLive(1);
////                    entity.setTags(channel.getTag());
////                    entity.setTitle(channel.getTitle());
////                    entity.setSellerId(myUserDTO.getId());
////                    entity.setName(entity.getName());
////                    entity.setAvatar(entity.getAvatar());
////                    channelLiveRedisRepository.save(entity);
////                    session.sendMessage(message);
//////                    textMessage = TextMessageBuilder
//////                            .getBuilder()
//////                            .set("topic", payload.getTopic())
//////                            .set("views", channel.getSubscribers().size())
//////                            .set("channel", entity)
//////                            .build();
//////                    session.sendMessage(textMessage);
////                    logger.info(String.format("Seller id %d start live success", myUserDTO.getId()));
//                    break;
//
//                case "LIVE_END":
//                    logger.info(String.format("Seller id %d request end live", myUserDTO.getId()));
//
//                    if (myUserDTO.getMode() != LoginModeEnum.SELLER.getMode()) return;
//                    channel = objectMapper.readValue(payloadData, ChannelLive.class);
//                    channelLiveService.endLive(channel.getId());
//                    channelLiveRedisRepository.deleteById(channel.getId());
//                    message = TextMessageBuilder.getBuilder()
//                            .set("topic", payload.getTopic())
//                            .build();
//                    sendMessageToRoom(channel.getSubscribers(), message);
//                    logger.info(String.format("Seller id %d end live success", myUserDTO.getId()));
//
//                    break;
//
//                case "LIVE_SNAP_BACKGROUND":
////                    logger.info(String.format("Seller id %d request snap background", myUserDTO.getId()));
////                    if (myUserDTO.getMode() != LoginModeEnum.SELLER.getMode()) return;
////                    channel = objectMapper.readValue(payloadData, ChannelLive.class);
////                    snapshot.channelId(channel.getId().intValue());
////                    message = TextMessageBuilder.getBuilder()
////                            .set("topic", payload.getTopic())
////                            .build();
////                    sendMessageToRoom(channel.getSubscribers(), message);
////                    logger.info(String.format("Seller id %d snap background success", myUserDTO.getId()));
//
//                    break;
//
//                case "EVENT_START":
//                    logger.info(String.format("Seller id %d request start event", myUserDTO.getId()));
//
//                    channel = objectMapper.readValue(payloadData, ChannelLive.class);
//                    channel = channelLiveRedisRepository.findById(channel.getId()).orElseThrow(() -> new ClientException("Not Found Channel"));
//                    Onsale onsaleRedis = onsaleRedisRepository.findBySellerId((myUserDTO.getId())).orElseThrow(() -> new ClientException("Not Found Event"));
//                    OnsaleDTO onsale = onsaleRedis.getEntity();
//                    channel.setOnsale(onsale);
//
//                    long time = onsale.getCreatedDate().getTime() + onsale.getTime() * 1000 - System.currentTimeMillis();
//                    if (time <= 0) return;
//                    channelLiveRedisRepository.save(channel);
//                    textMessage = TextMessageBuilder.getBuilder()
//                            .set("topic", payload.getTopic())
//                            .set("event", onsale)
//                            .set("timeLeft", time / 1000)
//                            .build();
//                    sendMessageToRoom(channel.getSubscribers(), textMessage);
//                    logger.info(String.format("Seller id %d start event success", myUserDTO.getId()));
//
//                    break;
//
//                case "EVENT_END":
//                    logger.info(String.format("Seller id %d request end live", myUserDTO.getId()));
////
////                    channel = objectMapper.readValue(payloadData, ChannelLive.class);
////                    channel = channelLiveRedisRepository.findById(channel.getId()).orElseThrow((() -> new ClientException("Not Found Channel")));
////                    onsale = channel.getOnsale();
////                    if (onsale == null) return;
////                    if (channel.getOnsale().getType() == 0) {
////                        List<Bid> bids = channel.getBids();
////                        Bid bid = (bids == null || bids.size() == 0) ? null : bids.get(bids.size() - 1);
////                        Optional<User> userEntityOptional = userRedisRepository.findById(bid.getUserId());
////                        textMessage = TextMessageBuilder.getBuilder()
////                                .set("topic", "EVENT_BID_WIN")
////                                .set("buyer_win", userEntityOptional.isPresent() ?
////                                        userEntityOptional.get().getUser().getUsername() : null)
////                                .set("isExistsBuyerWin", userEntityOptional.isPresent())
////                                .build();
////                        sendMessageToRoom(channel.getSubscribers(), textMessage);
////                    }
////                    channel.setOnsale(null);
////                    channel.setBids(new ArrayList<>());
////                    channelLiveRedisRepository.save(channel);
////
////                    textMessage = TextMessageBuilder.getBuilder()
////                            .set("topic", payload.getTopic())
////                            .build();
////                    sendMessageToRoom(channel.getSubscribers(), textMessage);
////                    channel.setOnsale(null);
////                    channel.setBids(new ArrayList<>());
//                    logger.info(String.format("Seller id %d end event success", myUserDTO.getId()));
//
//                    break;
//
//                case "EVENT_BID_PLUS_TIME":
//                    logger.info(String.format("Seller id %d request plus time for auction", myUserDTO.getId()));
////
////
////                    channel = objectMapper.readValue(payloadData, ChannelLive.class);
////                    channel = channelLiveRedisRepository.findById(channel.getId()).orElseThrow((() -> new ClientException("Not Found Channel")));
////
////                    if (channel.getOnsale() == null
////                            || channel.getOnsale().getType() != 0) return;
////                    onsale = channel.getOnsale();
////
////                    time = onsale.getCreatedDate().getTime() + onsale.getTime() * 1000 - System.currentTimeMillis();
////                    if (time < 0) return;
////                    onsale.setTime(onsale.getTime() + 15);
////
////                    textMessage = TextMessageBuilder.getBuilder()
////                            .set("topic", payload.getTopic())
////                            .set("timeLeft", time / 1000 + 15)
////                            .build();
////                    sendMessageToRoom(channel.getSubscribers(), textMessage);
//                    logger.info(String.format("Seller id %d  plus time for auction success", myUserDTO.getId()));
//
//                    break;
//
//                case "EVENT_BID_RESULT":
//                    channel = objectMapper.readValue(payloadData, ChannelLive.class);
//                    channel = channelLiveRedisRepository.findById(channel.getId()).orElseThrow((() -> new ClientException("Not Found Channel")));
//                    onsale = channel.getOnsale();
//                    if (onsale == null || onsale.getType() != 0) return;
//                    time = onsale.getCreatedDate().getTime() + onsale.getTime() * 1000 - System.currentTimeMillis();
//                    if (time > 0) return;
//                    List<Bid> bids = channel.getBids();
//                    Bid bid = bids.size() > 0 ? bids.get(bids.size() - 1) : null;
//
//                    MyUserDTO userWin = null;
//                    if (bid != null && bid.getUserId() != null) {
//                        Optional<User> userEntityOptional = userRedisRepository.findById(bid.getUserId());
//                        if (userEntityOptional.isPresent()) {
//                            userWin = userEntityOptional.get().getUser();
//                        }
//                    }
//
//                    textMessage = TextMessageBuilder.getBuilder()
//                            .set("buyer_win", userWin != null ?
//                                    userWin.getUsername() : null)
//                            .set("isExistsBuyerWin", userWin != null)
//                            .set("topic", payload.getTopic())
//                            .set("timeLeft", time / 1000)
//                            .set("timeEnd", time <= 0)
//                            .build();
//                    sendMessageToRoom(channel.getSubscribers(), textMessage);
//                    break;
//
//                case "CHANNEL_GET_ALL_BIDS":
//                    channel = objectMapper.readValue(payloadData, ChannelLive.class);
//                    channel = channelLiveRedisRepository.findById(channel.getId()).orElseThrow((() -> new ClientException("Not Found Channel")));
//                    bids = channel.getBids();
//                    textMessage = TextMessageBuilder.getBuilder()
//                            .set("topic", payload.getTopic())
//                            .set("bids", bids)
//                            .build();
//                    sendMessageToRoom(channel.getSubscribers(), textMessage);
//                    break;
//
//
//                case "CHANNEL_JOIN":
//
//                    channel = objectMapper.readValue(payloadData, ChannelLive.class);
//                    logger.info(String.format("User id %d request join channel %d", myUserDTO.getId(), channel.getId()));
//                    channel = channelLiveRedisRepository.findById(channel.getId()).orElseThrow(() -> new ClientException("Not Found Channel"));
//                    if (channel.getLive() == 0) throw new ClientException("CHANNEL not start");
//                    channel.getSubscribers().add(myUserDTO.getId());
//                    sessionUser.setChannelId(channel.getId());
//                    channel.getSubscribers().add(myUserDTO.getId());
//                    channelLiveRedisRepository.save(channel);
//                    textMessage = TextMessageBuilder.getBuilder()
//                            .set("topic", payload.getTopic())
//                            .set("views", channel.getSubscribers().size())
//                            .set("user", myUserDTO.getUsername()).build();
//                    sendMessageToRoom(channel.getSubscribers(), textMessage);
//
//                    message = TextMessageBuilder.getBuilder()
//                            .set("topic", "CHANNEL_ALL_MESSAGE")
//                            .set("messages", channel.getMessages())
//                            .build();
//                    session.sendMessage(message);
//
//                    Message msg = new Message();
//                    msg.setFrom(myUserDTO.getUsername());
//                    msg.setMessage("has joined LIVE");
//
//                    message = TextMessageBuilder.getBuilder()
//                            .set("topic", "CHANNEL_MESSAGE")
//                            .set("message", msg)
//                            .build();
//                    if (channel.getSellerId() == null || !channel.getSellerId().equals(myUserDTO.getId()))
//                        sendMessageToRoom(channel.getSubscribers(), message);
//
//                    if (channel.getOnsale() != null) {
//                        onsale = channel.getOnsale();
//                        time = onsale.getCreatedDate().getTime() + onsale.getTime() * 1000 - System.currentTimeMillis();
//                        if (time <= 0) return;
//
//                        TextMessage eventMessage = TextMessageBuilder.getBuilder()
//                                .set("topic", "EVENT_START")
//                                .set("event", onsale)
//                                .set("timeLeft", time / 1000).build();
//                        session.sendMessage(eventMessage);
//                    }
//                    ChannelView channelView = channelViewRedisRepository.findById(channel.getId()).orElse(new ChannelView());
//                    if (channelView.getId() == null) {
//                        channelView.setId(channel.getId());
//                    }
//                    userRedisRepository.save(sessionUser);
//                    channelView.setViews(channel.getSubscribers().size());
//                    channelViewRedisRepository.save(channelView);
//                    logger.info(String.format("User id %d join channel %d success", myUserDTO.getId(), channel.getId()));
//
//                    break;
//
//                case "CHANNEL_EXIT":
//                    channel = objectMapper.readValue(payloadData, ChannelLive.class);
//                    channel = channelLiveRedisRepository.findById(channel.getId()).orElseThrow(() -> new ClientException("Not Found Channel"));
//                    Long userId = myUserDTO.getId();
//                    channel.getSubscribers().removeIf(e -> e.equals(userId));
//                    channelLiveRedisRepository.save(channel);
//                    channelView = channelViewRedisRepository.findById(channel.getId()).orElse(new ChannelView());
//                    if (channelView.getId() == null) {
//                        channelView.setId(channel.getId());
//                    }
//                    channelView.setViews(channel.getSubscribers().size());
//                    channelViewRedisRepository.save(channelView);
//                    sessionUser.setChannelId(null);
//                    userRedisRepository.save(sessionUser);
//                    textMessage = TextMessageBuilder.getBuilder()
//                            .set("topic", "CHANNEL_EXIT")
//                            .set("views", channel.getSubscribers().size())
//                            .set("user", myUserDTO.getUsername()).build();
//                    sendMessageToRoom(channel.getSubscribers(), textMessage);
//                    break;
//
//                case "CHANNEL_MESSAGE":
//                    msg = objectMapper.readValue(payload.getData(), Message.class);
//                    logger.info(String.format("User id %d request send message to channel %d", myUserDTO.getId(), msg.getTo()));
//                    channel = channelLiveRedisRepository.findById(msg.getTo()).orElseThrow(() -> new ClientException("Not Found Channel"));
//                    msg.setFrom(myUserDTO.getUsername());
//                    channel.getMessages().add(msg);
//                    channelLiveRedisRepository.save(channel);
//                    textMessage = TextMessageBuilder.getBuilder()
//                            .set("topic", payload.getTopic())
//                            .set("message", msg).build();
//                    sendMessageToRoom(channel.getSubscribers(), textMessage);
//                    logger.info(String.format("User id %d send message to channel %d success", myUserDTO.getId(), msg.getTo()));
//
//                    break;
//
//                case "CHANNEL_HEART":
//                    msg = objectMapper.readValue(payloadData, Message.class);
//                    channel = channelLiveRedisRepository.findById(msg.getTo()).orElseThrow(() -> new ClientException("Not Found Channel"));
//                    sendMessageToRoom(channel.getSubscribers(), message);
//                    break;
//
//                case "CHANNEL_ALL_BIDS":
//                    channel = objectMapper.readValue(payloadData, ChannelLive.class);
//                    channel = channelLiveRedisRepository.findById(channel.getId()).orElseThrow(() -> new ClientException("Not Found Channel"));
//
//                    textMessage = TextMessageBuilder.getBuilder()
//                            .set("topic", payload.getTopic())
//                            .set("bids", channel.getBids()).build();
//                    session.sendMessage(textMessage);
//                    break;
//
//                case "CHANNEL_BID":
////                    bid = objectMapper.readValue(payloadData, Bid.class);
////                    if (bid.getAmount() < 0) return;
////                    bid.setUserId(myUserDTO.getId());
////                    bid.setUsername(myUserDTO.getUsername());
////                    bid.setDate(new Date());
////                    channel = channelLiveRedisRepository.findById(msg.getTo()).orElseThrow(()-> new ClientException("Not Found Channel"));
////
////                    if (channel == null || channel.getOnsale() == null || channel.getOnsale().getType() != 0) return;
////                    onsale = channel.getOnsale();
////                    time = onsale.getCreatedDate().getTime() + onsale.getTime() * 1000 - System.currentTimeMillis();
////                    if (time <= 0) return;
////                    bids = channel.getBids();
////                    if (bids.size() == 0) {
////                        channel.getBids().add(bid);
////                    } else {
////                        Bid lastBid = channel.getBids().get(channel.getBids().size() - 1);
////                        if (lastBid.getAmount() > bid.getAmount()) return;
////                        channel.getBids().add(bid);
////                    }
////
////                    textMessage = TextMessageBuilder.getBuilder()
////                            .set("topic", payload.getTopic())
////                            .set("lastBid", bid).build();
////                    sendMessageToRoom(channel.getSubscribers(), textMessage);
//                    break;
//            }
//        } catch (Exception e) {
//            logger.info(e.getStackTrace());
//            e.printStackTrace();
//            message = TextMessageBuilder.getBuilder()
//                    .set("message", e.getMessage())
//                    .build();
//            session.sendMessage(message);
//        }
//    }
//
//    public void sendMessageToRoom(Set<Long> list, TextMessage message) {
//        sendMessageToRoom(list, message, new HashSet<>());
//    }
//
//    public void sendMessageToRoom(Set<Long> list, TextMessage message, Set<Long> ignoreIds) {
//        if (list == null || list.size() == 0) return;
//        list.parallelStream().forEach(inRoom -> {
//            try {
//                if (!ignoreIds.contains(inRoom)) {
//                    WebSocketSession session = dataUtils.sessions.get(inRoom);
//                    if (session != null) session.sendMessage(message);
//                }
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
//    }
//
//    @Override
//    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//        //the messages will be broadcasted to all users.
//        System.out.println(session.getId());
////        dataUtils.sessions.put(session.getId(), session);
//        Map<String, Object> map = new HashMap<>();
//        map.put("session_id", session.getId());
//        map.put("topic", "CONNECT");
//        session.sendMessage(new TextMessage(objectMapper.writeValueAsString(map)));
//        System.out.println(map);
//    }
//
//    @Override
//    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
//        System.out.println("Transport ERROR");
//        super.handleTransportError(session, exception);
//    }
//
//    @Override
//    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//        User sessionUser = userRedisRepository.findBySessionId(session.getId()).orElseThrow(() -> new Exception("Not found user"));
//        if (sessionUser.getChannelId() != null) {
//            ChannelLive channelLive = channelLiveRedisRepository.findById(sessionUser.getId()).get();
//            channelLive.getSubscribers().remove(sessionUser.getChannelId());
//            channelLiveRedisRepository.save(channelLive);
//            ChannelView channelView = channelViewRedisRepository.findById(channelLive.getId()).orElse(new ChannelView());
//            if (channelView.getId() == null) {
//                channelView.setId(channelLive.getId());
//            }
//            channelView.setViews(channelLive.getSubscribers().size());
//            channelViewRedisRepository.save(channelView);
//            TextMessage textMessage = TextMessageBuilder.getBuilder()
//                    .set("topic", "CHANNEL_EXIT")
//                    .set("views", sessionUser.getChannelId())
//                    .build();
//            sendMessageToRoom(channelLive.getSubscribers(), textMessage);
//            sessionUser.setChannelId(null);
//            userRedisRepository.save(sessionUser);
//        }
//    }
//}