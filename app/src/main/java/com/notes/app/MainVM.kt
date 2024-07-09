package com.notes.app

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.painter.Painter
import androidx.lifecycle.ViewModel
import com.notes.app.data.Note
import kotlinx.collections.immutable.ImmutableList


//class MainVM: ViewModel() {
//    fun initializeModel(context: Context) {
//
//    }
//
//    // Of MainPage
//    var isDrawerOpen by mutableStateOf(false); private set
//    var askConfirmFor: ServerUiState? by mutableStateOf(null); private set
//    var showIncompatibleFor: ServerUiState? by mutableStateOf(null); private set
//
//    private fun handelMainAction(action: MainAct, context: Context?) {
//        when(action) {
//            is MainAct.NavToDashboardPrs -> onFragment = Fragments.Dashboard
//            is MainAct.NavToMenuPrs -> onFragment = Fragments.Menu
//            is MainAct.ScanButtonPrs -> {
//                if(connState == ConnState.Connected)
//                    Bridge.disconnect()
//                else Bridge.updateNetworksAndScan()
//            }
//            is MainAct.ScanButtonLPrs -> isScanBarOpen = true
//            is MainAct.ScanBarDismissed -> isScanBarOpen = false
//
//            is MainAct.ACDlgConnPrs -> {
//                askConfirmFor = null
//                BridgeData.remember(action.serverId, action.pass)
//                BridgeData.unAvoidConnectTo(action.serverId)
//                serversAvailableNow[action.serverId]?.let {
//                    Bridge.connectTo(it)
//                }
//            }
//            is MainAct.ACDlgCancelPrs -> {
//                askConfirmFor = null
//                BridgeData.avoidConnectTo(action.serverId)
//            }
//            is MainAct.ICDlgGtReleasePrs -> context?.goToLink(linkServerRelease)
//            is MainAct.ICDlgCancelPrs -> {
//                showIncompatibleFor = null
//                BridgeData.avoidConnectTo(action.serverId)
//            }
//        }
//    }
//
//    var isScanning by mutableStateOf(false)
//    var connState by mutableStateOf(ConnState.Idle)
//    var serverList by mutableStateOf(persistentListOf<ServerUiState>())
//    var networkList by mutableStateOf(persistentSetOf<NetworkAddress>())
//    private var targetServer: ServerInfo? = null
//    private var serversAvailableNow: Map<Long, ServerInfo> = hashMapOf()
//    private val bridgeCallback = object: BridgeCallback {
//        fun updateServerList() {
//            serverList = mutableListOf<ServerUiState>().apply {
//                serversAvailableNow.values.forEach {
//                    if(it != targetServer) add(it.toUiState())
//                    else add(0, it.toUiState(connState))
//                }
//            }.toPersistentList()
//        }
//        override fun onScanStarted() { isScanning = true }
//        override fun onScanEnded() { isScanning = false }
//        override fun onNetworksUpdated(inNetworks: Set<NetworkAddress>) {
//            networkList = inNetworks.toPersistentSet()
//        }
//        override fun onScanUpdated(availableServer: HashMap<Long, ServerInfo>) {
//            serversAvailableNow = availableServer
//            updateServerList()
//            if (connState != ConnState.Connected)
//                for (server in availableServer.values) {
//                    if(BridgeData.isAvoided(server.id)) continue
//                    if(!BridgeData.isKnown(server.id)) isScanBarOpen = true
//                    else Bridge.connectTo(server)
//                    break
//                }
//        }
//        override fun onConnecting(toServer: ServerInfo, isInitial: Boolean) {
//            connState = ConnState.Connecting
//            targetServer = toServer
//            updateServerList()
//        }
//        override fun onConnectResult(result: ConnResult, toServer: ServerInfo, isInitial: Boolean) {
//            if(result == ConnResult.Connected) {
//                connState = ConnState.Connected
//                showHowToConnect = false
//                Bridge.stopScanning()
//                if(!AppUsageData.haveConnectedBefore) {
//                    AppUsageData.haveConnectedBefore = true
//                    Analytics.userConnected()
//                }
//            }
//            else connState = ConnState.Idle
//            targetServer = toServer
//            updateServerList()
//        }
//        override fun onDisconnected(by: DisConnBy, fromServer: ServerInfo) {
//            connState = ConnState.Idle
//            targetServer = null
//            updateServerList()
//        }
//    }
//    init { Bridge.registerCallback(bridgeCallback) }
//    private fun handelScanBarActions(action: ScanBarAct, context: Context?) {
//        when (action) {
//            is ScanBarAct.ScanButtonPrs -> Bridge.updateNetworksAndScan()
//            is ScanBarAct.ServerPrs -> {
//                BridgeData.unAvoidConnectTo(action.id)
//                if(connState == ConnState.Connected && action.id == targetServer?.id)
//                    context?.makeToast(R.string.bridge_alreadyConnected_msg)
//                else if(connState != ConnState.Connecting) {
//                    val server = serversAvailableNow[action.id]
//                    if(server != null) when {
//                        !Bridge.isCompatible(server.version) -> showIncompatibleFor = server.toUiState()
//                        !BridgeData.isKnown(server.id) -> askConfirmFor = server.toUiState()
//                        else -> Bridge.connectTo(server)
//                    }
//                }
//            }
//            is ScanBarAct.ServerLPrs -> if(context != null) {
//                val latency = serversAvailableNow[action.id]?.latency ?: 0
//                val delayText = R.string.server_delay.sRes(context, latency)
//                context.makeToast(delayText)
//            }
//            is ScanBarAct.ServerDisConPrs -> {
//                BridgeData.avoidConnectTo(action.id)
//                Bridge.disconnect()
//            }
//            is ScanBarAct.ServerForgetPrs -> {
//                BridgeData.forget(action.id)
//                Bridge.disconnect()
//            }
//        }
//    }
//
//    val listOfGames = mutableStateListOf<AboutSegment>()
//    val listOfTools = mutableStateListOf<AboutSegment>()
//    var showHowToConnect by mutableStateOf(false); private set
//    var askToReviewTheApp by mutableStateOf(false); private set
//    var showAboutCustomLayouts by mutableStateOf(false); private set
//    fun askToReviewIfCan() {
//        when(AppUsageData.reviewStatus) {
//            ReviewState.NotAsked -> if(AppUsageData.canAskToReview()) {
//                askToReviewTheApp = true
//                AppUsageData.reviewStatus = ReviewState.Asking
//                Analytics.reviewStChanged(ReviewState.Asking)
//            }
//            ReviewState.Asking -> askToReviewTheApp = true
//            else -> askToReviewTheApp = false
//        }
//    }
//    private fun handelDashboardActions(action: DashAct, context: Context?) {
//        when (action) {
//            is DashAct.SegmentPrs -> {
//                val segment = if(action.isGames) when(action.segIdx) {
//                    0 -> GtaSegment
//                    1 -> RdrSegment
//                    2 -> WdSegment
//                    3 -> ForzaSegment
//                    else -> null
//                }
//                else when(action.segIdx) {
//                    0 -> BasicSegment
//                    1 -> XboxSegment
//                    2 -> PpSegment
//                    3 -> {
//                        context?.startActivity(Intent(context, SegmentActivity::class.java))
//                        return
//                    }
//                    else -> null
//                }
//                if(segment != null) {
//                    context?.startSegment(segment)
//                    if(Bridge.isAlive) {
//                        val segType = if (segment.isGame) SegType.Game else SegType.Tool
//                        Analytics.usedSegment(segment.name, segType)
//                    }
//                }
//            }
//            is DashAct.SegmentLPrs -> context?.makeToast("Built-In")
//            is DashAct.HtcStep1Prs -> onFragment = Fragments.Menu
//            is DashAct.HtcNotePrs -> {
//                context?.openWebPage(
//                    "$linkLocalTextGuide#ServerNotVisible",
//                    R.string.textGuide_title.sRes(context)
//                )
//                Analytics.facedIssueOnConn()
//            }
//            is DashAct.HtcTextGuidePrs -> {
//                context?.openWebPage(linkLocalTextGuide, R.string.textGuide_title.sRes(context))
//                Analytics.usingGuide(GuideType.LocalDocs)
//            }
//            is DashAct.HtcVideoGuidePrs -> {
//                context?.goToLink(linkVideoGuide)
//                Analytics.usingGuide(GuideType.YtVideo)
//            }
//            is DashAct.RateAppPrs -> {
//                askToReviewTheApp = false
//                context?.goToLink(linkAppInStore)
//                context?.makeToast(R.string.dash_rateAppSection_onRateMsg.sRes(context))
//                AppUsageData.reviewStatus = ReviewState.Reviewed
//                Analytics.reviewStChanged(ReviewState.Reviewed)
//            }
//            is DashAct.NeverAskToRatePrs -> {
//                askToReviewTheApp = false
//                AppUsageData.reviewStatus = ReviewState.Denied
//                Analytics.reviewStChanged(ReviewState.Denied)
//            }
//        }
//    }
//    private fun handelMenuActions(action: MenuAct, context: Context?) = when(action) {
//        is MenuAct.SendFeedbackPrs -> context?.writeGmail(
//            arrayOf(supportMailId),
//            R.string.menu_sendFeedback_subject.sRes(context)
//        )
//        is MenuAct.CheckForUpdatePrs -> context?.goToLink(linkAppInStore)
//        is MenuAct.HowToUsePrs -> {
//            showHowToConnect = true
//            onFragment = Fragments.Dashboard
//        }
//        is MenuAct.GtSettingsPrs -> context?.goToActivity(SettingsActivity::class.java)
//        is MenuAct.GtAboutAppPrs -> context?.goToActivity(AboutActivity::class.java)
//        is MenuAct.GtPrivacyPolicyPrs -> context?.openWebPage(
//            linkLocalPrivacyPolicy,
//            R.string.privacyPolicy_title.sRes(context)
//        )
//        is MenuAct.GtReleasePrs -> {
//            context?.goToLink(linkServerRelease)
//            Analytics.gettingServer(By.GithubRelease)
//        }
//        is MenuAct.DLdServerPrs -> {
//            context?.goToLink(linkDownloadServer)
//            Analytics.gettingServer(By.DirectDownload)
//        }
//        is MenuAct.CpServerLnkPrs -> {
//            context?.copyText(linkDownloadServer, R.string.menu_downloadServer_clipboard_onCopied_msg.sRes(context))
//            Analytics.gettingServer(By.CopyingLink)
//        }
//
//        is MenuAct.JnTelegramPrs -> {
//            context?.goToLink(linkTelegram)
//            Analytics.joiningCommunity(JoinIn.Telegram)
//        }
//        is MenuAct.JnDiscordPrs -> {
//            context?.makeToast(R.string.menu_joinCommunity_noAvailable_msg)
//            Analytics.joiningCommunity(JoinIn.Discord)
//        }
//    }
//
//    //Of AboutPage
//    private fun handelAboutActions(action: AboutAct, context: Context?) {
//        when(action) {
//            is AboutAct.AboutDevPrs -> context?.goToLink(linkDevLinkedIn)
//            is AboutAct.AboutPoweredByPrs -> context?.goToLink(
//                link = linksOfPoweredBy.getOrNull(action.ofIndex) ?:
//                linkAppInStore
//            )
//            is AboutAct.ShareAppPrs -> context?.shareMessage(
//                R.string.about_supportApp_share_subject.sRes(context),
//                message = "${R.string.about_supportApp_share_subject.sRes(context)}\n$linkAppInStore",
//                selectTitle = R.string.about_supportApp_share_chooseAppMsg.sRes(context)
//            )
//            is AboutAct.RateAppPrs -> context?.goToLink(linkAppInStore)
//            is AboutAct.GoBackPrs -> (context as? Activity)?.finishAndRemoveTask()
//        }
//    }
//
//    private fun handelOtherAction(action: OtherAct, context: Context?) {
//        when(action) {
//            is OtherAct.GoToLink -> context?.goToLink(action.url)
//            is OtherAct.GoToActivity -> context?.goToActivity(action.cls)
//            is OtherAct.OpenWebPage -> context?.openWebPage(action.url, action.title)
//            is OtherAct.GoBack -> (context as? Activity)?.finishAndRemoveTask()
//            is OtherAct.CopyText -> context?.copyText(action.text, action.label)
//            is OtherAct.MakeToast -> context?.makeToast(action.message)
//        }
//    }
//    fun handelMainBackAction() = when {
//        isScanBarOpen -> { isScanBarOpen = false; true }
//        else -> false
//    }
//    fun handelAction(action: UiAction, context: Context?) {
////        println("\nonAction Called($action) at ${System.currentTimeMillis()}")
//        when(action) {
//            is MainAct -> handelMainAction(action, context)
//            is ScanBarAct -> handelScanBarActions(action, context)
//            is DashAct -> handelDashboardActions(action, context)
//            is MenuAct -> handelMenuActions(action, context)
//            is AboutAct -> handelAboutActions(action, context)
//            is OtherAct -> handelOtherAction(action, context)
//            else -> {}
//        }
//    }
//
//    override fun onCleared() {
//        Bridge.unRegisterCallback(bridgeCallback)
//        super.onCleared()
//    }
//}